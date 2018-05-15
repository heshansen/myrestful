package com.topbaby.service.impl;

import com.topbaby.ecommerce.brandshop.entity.BrandshopStock;
import com.topbaby.ecommerce.brandshop.service.BrandshopStockService;
import com.topbaby.ecommerce.brandshop.vo.BrandshopStocksProdutVO;
import com.topbaby.ecommerce.product.entity.ProductSKU;
import com.topbaby.ecommerce.product.service.ProductSKUAttributeValueService;
import com.topbaby.ecommerce.product.service.ProductSKUService;
import com.topbaby.ecommerce.product.service.ProductService;
import com.topbaby.entity.BrandshopUserProductsInfo;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BrandshopRestService;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xh
 */
@Service
@Transactional
public class BrandshopRestServiceImpl implements
        BrandshopRestService {


    @Autowired
    private BrandshopStockService brandshopStockService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSKUService productSKUService;
    @Autowired
    private ProductSKUAttributeValueService productSKUAttributeValueService;

    public BaseResponseMsg getProductList(PageQueryModel pageQueryModel, Map param) throws CoreException {
        PageModel<BrandshopStock> brandshopStockList = null;
        // 库存list
        List<BrandshopStock> stockList;
        BrandshopUserProductsInfo brandshopUserProductsInfo = new BrandshopUserProductsInfo();
        List<BrandshopStocksProdutVO> brandshopStocksProdutVoList = new ArrayList<BrandshopStocksProdutVO>();

        brandshopStockList = brandshopStockService.getBrandShopProductList(param, pageQueryModel);


        stockList = brandshopStockList.getDataList();
        BrandshopStocksProdutVO brandshopStocksProdutVo;
        for (BrandshopStock brandshopStock : stockList) {

            brandshopStocksProdutVo = new BrandshopStocksProdutVO();
            brandshopStocksProdutVo.setBrandshopStock(brandshopStock);
            brandshopStocksProdutVo.setProduct(productService.getModel(brandshopStock.getProductId()));

            // 根据skuId获得该sku所对应的所有属性返回List<Map>
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("skuSeq", brandshopStock.getSkuId());
            List<Map<String, Object>> kuValueList = productSKUAttributeValueService
                    .getSkuValueById(params);
            List<String> stringkuValue = new ArrayList<String>();

            // 这里将遍历所有属性的List<Map>，然后取出sku属性所对应的属性值
            for (Map<String, Object> map : kuValueList) {
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    if (m.getKey().toString().equals("skuAttrValue")) {
                        stringkuValue.add(m.getValue().toString());
                    }
                }
            }
            // 属性值之间用 ，分割开
            StringBuilder result = null;
            if (stringkuValue != null && stringkuValue.size() > 0) {
                result = new StringBuilder();
                boolean flag = false;
                for (String string : stringkuValue) {
                    if (flag) {
                        result.append(",");
                    } else {
                        flag = true;
                    }
                    result.append(string);
                    brandshopStocksProdutVo.setProductSKUAttributeValue(result.toString());
                }
            }
            brandshopStocksProdutVoList.add(brandshopStocksProdutVo);
            brandshopUserProductsInfo.setBrandshopStocksProdutVOs(brandshopStocksProdutVoList);
            brandshopUserProductsInfo.setTotalRecord(brandshopStockList.getTotalRecord());
            brandshopUserProductsInfo.setPageQueryModel(brandshopStockList.getPageQueryModel());
        }
        return brandshopUserProductsInfo;
    }

    public BaseResponseMsg updateRepCount(Long id, int stock, String productNo) throws CoreException {
        BrandshopStock bss = null;

        if (productNo.equals("1")) {
            bss = brandshopStockService.getModel(id);
            bss.setStock(stock);
            bss.setUpdateDate(new Date());
            bss.setUpdateTime(new Date());

            brandshopStockService.update(bss);

        } else {

            BrandshopStock oldStock = brandshopStockService.getModel(id);
            Map<String, Object> param2 = new HashMap<String, Object>();
            param2.put("prodSeq", oldStock.getProductId());
            List<ProductSKU> list2 = productSKUService.getList(param2);
            List<Long> skuArray = new ArrayList<Long>();
            if (list2 != null) {
                for (int i = 0; i < list2.size(); i++) {
                    // 取出所有的sku保存在集合中
                    skuArray.add(list2.get(i).getId());
                }
                // 批量修改门店上架数
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("skuArray", skuArray);
                params.put("stock", stock);
                params.put("brandshopSeq", brandshopStockService.getModel(oldStock.getId())
                        .getBrandshop().getId());
                params.put("productSeq", brandshopStockService.getModel(oldStock.getId())
                        .getProductId());
                params.put("brandshopPrice", brandshopStockService.getModel(oldStock.getId())
                        .getBrandshopPrice());
                params.put("price", brandshopStockService.getModel(oldStock.getId()).getPrice());

                brandshopStockService.batchUpdate(params);
            }
        }
        return new BaseResponseMsg();
    }


}
