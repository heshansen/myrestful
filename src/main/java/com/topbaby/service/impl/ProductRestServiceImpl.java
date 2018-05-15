package com.topbaby.service.impl;

import com.topbaby.common.TopConstants;
import com.topbaby.ecomerce.utils.BeanUtils;
import com.topbaby.ecomerce.utils.JsonUtils;
import com.topbaby.ecommerce.core.Constants;
import com.topbaby.ecommerce.merchant.entity.Merchant;
import com.topbaby.ecommerce.merchant.entity.MerchantUser;
import com.topbaby.ecommerce.merchant.service.MerchantProductService;
import com.topbaby.ecommerce.merchant.service.MerchantService;
import com.topbaby.ecommerce.product.dao.ProductSKUDao;
import com.topbaby.ecommerce.product.entity.*;
import com.topbaby.ecommerce.product.service.ProductCategoryAttributeService;
import com.topbaby.ecommerce.product.service.ProductCategoryService;
import com.topbaby.ecommerce.product.service.ProductSKUService;
import com.topbaby.ecommerce.product.service.ProductService;
import com.topbaby.ecommerce.verify.entity.Verify;
import com.topbaby.entity.ProductInfo;
import com.topbaby.entity.param.ProductPageParam;
import com.topbaby.entity.ProductSKUInfo;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.entity.result.CommonPostResult;
import com.topbaby.entity.result.failinfo.ProductFailInfo;
import com.topbaby.entity.result.failinfo.ProductSKUFailInfo;
import com.topbaby.service.BrandCategoryService;
import com.topbaby.service.BrandRestService;
import com.topbaby.service.ProductRestService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.model.IPageQueryModel;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.common.StringUtils;
import org.esbuilder.sequence.ISequenceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * details：商品 restful service 实现类
 * Created by sxy on 16-11-29.
 */
@Service
@Transactional
public class ProductRestServiceImpl implements ProductRestService {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSKUService productSKUService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private BrandRestService brandRestService;
    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private MerchantProductService merchantProductService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductCategoryAttributeService productCategoryAttributeService;

    @Autowired
    private ProductSKUDao productSKUDao;

    @Resource(name = "productCodeFactory")
    private ISequenceFactory sequenceFactory;

    /**
     * <p>验证商品货号（商品）是否存在（唯一）</p>
     *
     * @param stockNo    商品货号
     * @param merchantID 商户ID
     * @return true-不存在；false-存在
     * @throws CoreException
     */
    public Boolean verifyProductStockNo(String stockNo, long merchantID) throws CoreException {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchantSeq", merchantID);
        param.put("stockNo", stockNo);
        param.put("withoutStatus", "withoutStatus");
        /** 查询该商户下、未注销（状态不为9）的、货号为stockNo 的商品列表 */
        List<Product> productList = productService.getProductList(param);
        return !CollectionUtils.isNotEmpty(productList);
    }

    /**
     * <p>验证SKU商品是否重复</p>
     *
     * @param productID            商品ID
     * @param productSKUAttrValues 商品sku属性值(json串) example:[{"prodSeq":"78","price":"759.00","bar":"83C501","attrData":[{"id":"119","value":"橙色"},{"id":"311","value":"80（1-2岁）"}]},{"prodSeq":"78","price":"759.00","bar":"83C501","attrData":[{"id":"119","value":"橙色"},{"id":"311","value":"90（2-3岁）"}]}]
     * @return true-不存在重复数据；false-存在重复数据
     */
    public Boolean verifyProductSKU(String productID, String productSKUAttrValues) throws Exception {

        // TODO: 16-11-29 算法待进一步提高
        /** 判断sku商品是否重复,通过对比sku属性值拼接的字符串来判断是否相等,存在相等的就退出 */
        int productListNum = 0;
        Set<String> skuStrSet = getProductSKUListStr(productSKUAttrValues);

        productListNum = skuStrSet.size();
        /** 获取当前存在的sku商品列表 */
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("prodSeq", productID);
        List<ProductSKU> productSKUList = productSKUDao.selectList(param);

        for (ProductSKU tmpProductSKU : productSKUList) {
            skuStrSet.add(tmpProductSKU.getSkuValStr().trim());
        }

        if (skuStrSet.size() != (productListNum + productSKUList.size())) {
            // TODO: 16-11-29 添加日志
            System.out.println("=== 存在重复的sku商品 ===");
            return false;
        }

        return true;
    }

    /**
     * <p> 添加商品SKU属性 </p>
     *
     * @param productID            商品ID
     * @param productSKUAttrValues 商品sku属性值(json串)
     * @param request
     * @return
     * @throws Exception
     */
    public Boolean addProductSKU(String productID, String productSKUAttrValues, HttpServletRequest request) throws Exception {

        List<Map<String, Object>> skuMapList = new ArrayList<Map<String, Object>>();
        if (!StringUtils.isNullOrEmpty(productSKUAttrValues)) {
            skuMapList = JsonUtils.parseJSON2List(productSKUAttrValues);
        }

        /** 添加商品ID */
        for (Map<String, Object> tempSKUMap : skuMapList) {
            if (tempSKUMap.containsKey(TopConstants.SKULISTPRODNAME)) {
                tempSKUMap.put(TopConstants.SKULISTPRODNAME, productID);
            }
        }

        /** 处理上传文件 */
        Map<String, MultipartFile> formFileData = new HashMap<String, MultipartFile>();

        if (request != null && request.getSession() != null) {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    //取得上传文件
                    String str = iter.next();
                    MultipartFile file = multiRequest.getFile(str);
                    if (file != null) {
                        formFileData.put(str.toString(), file);
                    }
                }
            }
        }

        /** 批量添加sku属性 */
        if (skuMapList.size() != 0) {
            productSKUService.batchAddExt(skuMapList, productID, formFileData);
        }
        return true;
    }

    /**
     * <p>添加商品</p>
     *
     * @param product           商品对象
     * @param productAttrValues 商品基础属性值（json串）
     * @param productImageData  商品图片信息
     * @param productDetailHtml 商品详情图片（一张）字节流
     * @param merchantID        商户ID
     * @param prodInfos         证件类型，仅门票类商品(prodType = 1)有效
     * @return true-添加成功；false-添加失败
     */
    public Boolean addProduct(Product product, String productAttrValues, ProductImageData productImageData, String productDetailHtml, long merchantID, Long[] prodInfos) throws Exception {

        /** 提货方式 ：'1'为自提，“2”为快递，“3”两种提货方式都提供 */
        if (product.getOfferDelivery().equals("1,2") || product.getOfferDelivery().equals("1，2")) {
            product.setOfferDelivery("3");
        }
        product.setCreateDate(new Date());
        product.setStatus(Constants.PRODUCT_STATUS_NEW);
        product.setProductNo(this.sequenceFactory.generate().toString());
        product.setMerchantSeq(merchantID);

        /** 将商品基础属性值json串转换成map对象 */
        List<Map<String, Object>> productAttributesValueList = new ArrayList<Map<String, Object>>();
        if (!StringUtils.isNullOrEmpty(productAttrValues)) {
            productAttributesValueList = JsonUtils.parseJSON2List(productAttrValues);
        }

        product = merchantProductService.addExt(product, productAttributesValueList, productImageData, productDetailHtml,null);

        // TODO: 16-12-28    product = merchantProductService.addExt(product, productAttributesValueList, productImageData, productDetailHtml);



        product = merchantProductService.addExt(product, productAttributesValueList, productImageData, productDetailHtml, prodInfos);
        

        if (product == null) {
            return false;
        }

        /** 添加商品审核记录 (merchantProductService 方法有局限，可修改)*/
        List<Product> productList = new ArrayList<Product>();
        // TODO: 16-11-29 获得merchantUser对象 待修改
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setId(Long.valueOf(666666));
        Merchant merchant = merchantService.getModel(merchantID);

        product.setMerchantSeq(merchantUser.getMerchantSeq());
        product.setCompanyName(merchant.getCompanyName());
        productList.add(product);

        productList = merchantProductService.submitToVerify(productList, merchantUser);

        if (productList == null) {
            return false;
        }

        return true;
    }

    /**
     * <p>根据 ID 获取商品信息</p>
     *
     * @param productID 商品ID
     * @return 返回商品对象（不存在-对象为null）
     */
    public Product getProductByID(long productID) {

        return productService.getModel(productID);
    }

    /**
     * <p>更新商品信息</p>
     *
     * @param product           商品对象
     * @param productAttrValues 商品基础属性值（json串）
     * @param productImageData  商品图片信息
     * @param productDetailHtml 商品详情图片（一张）字节流
     * @param merchantID        商户ID
     * @return true-更新成功；false-更新失败
     */
    public Boolean updateProduct(Product product, String productAttrValues, ProductImageData productImageData, String productDetailHtml, long merchantID) throws Exception {
        if (product.getId() == null) {
            //// TODO: 16-11-29 添加日志
            return false;
        }
        List<Map<String, Object>> productAttributesValueList = new ArrayList<Map<String, Object>>();
        if (!StringUtils.isNullOrEmpty(productAttrValues)) {
            productAttributesValueList = JsonUtils.parseJSON2List(productAttrValues);
        }
        Product targetProduct = productService.getModel(product.getId());
        BeanUtils.copyProperties(product, targetProduct);

        if (targetProduct.getStatus().equals(Constants.PRODUCT_STATUS_NEW) || targetProduct.getStatus().equals(Constants.PRODUCT_STATUS_REFUSE)) {
            targetProduct.setUpdateDate(new Date());
            targetProduct = productService.updateWithAttrAndImg(targetProduct, productAttributesValueList, productImageData, productDetailHtml);
            if (targetProduct == null) {
                return false;
            }
        } else {
            // TODO: 16-11-29 获得merchantUser对象
            MerchantUser merchantUser = new MerchantUser();
            merchantUser.setId(Long.valueOf(666666));

            Merchant merchant = merchantService.getModel(merchantID);
            targetProduct.setMerchantSeq(merchant.getId());
            targetProduct.setCompanyName(merchant.getCompanyName());

            /** 添加商品审核记录，暂不修改商品现有信息 */
            Verify tempVerify = merchantProductService.updateExt(targetProduct, productAttributesValueList, productImageData, productDetailHtml, merchantUser);
            if (tempVerify == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>根据ID注销商品</p>
     *
     * @param productID 商品ID
     * @return true-注销成功；false-注销失败
     */
    public Boolean cancelProductByID(long productID) throws Exception {

        Product product = productService.getModel(productID);
        if (product != null) {
            // TODO: 16-11-29  补充业务逻辑校验 如订单处理情况!!!!
            /** 注销商品 */
            product = merchantProductService.cancel(product);
        }

        if (product == null) {
            return false;
        }
        return true;
    }

    /**
     * <p>获取商品分页数据</p>
     *
     * @param productPageParam 筛选参数(merSeq-商户ID;brandSeq-品牌ID;status-状态 等)
     * @param pageQueryModel   分页数据信息(page,start,limit)
     * @return
     */
    public PageModel<Product> getProductPageList(ProductPageParam productPageParam, IPageQueryModel pageQueryModel) {

        Map<String, Object> param = new HashMap<String, Object>();
        ProductCategory productCategory = productCategoryService.getModel(productPageParam.getCatSeq());
        /** 获取三级种类列表 */
        if (productCategory != null) {
            if (productCategory.getLevel().equals("2")) {
                param.put("catSeq", productPageParam.getCatSeq());
            } else {
                List<Long> catSeqList = null;

                if (productPageParam.getCatSeq() != null) {
                    catSeqList = new ArrayList<Long>();
                    Long[] catSeqArray = productCategoryService.getDescendantsId(productPageParam.getCatSeq());
                    for (int i = 0; i < catSeqArray.length; i++) {
                        catSeqList.add(catSeqArray[i]);
                    }
                    if (catSeqList == null || catSeqList.size() == 0) {
                        catSeqList.add(productPageParam.getCatSeq());
                    }
                    param.put("catSeqls", catSeqList);
                }
            }
        }

        param.put("status", productPageParam.getStatus());
        param.put("brandSeq", productPageParam.getBrandSeq());
        param.put("merSeq", productPageParam.getMerSeq());

        param.put("productNoVague", StringUtils.trim(productPageParam.getProductNoVague()));
        param.put("stockNoVague", StringUtils.trim(productPageParam.getStockNoVague()));
        param.put("nameVague", StringUtils.trim(productPageParam.getNameVague()));

        /** 降序排列 */
        param.put(Constants.ORDERRULES, Constants.ORDERRULES);
        return productService.getPageList(param, pageQueryModel);
    }

    /**
     * <p>验证商品数据有效性</p>
     *
     * @param productInfoList {@link ProductInfo}
     * @return
     */
    public BaseResponseMsg verifyProductInfo(List<ProductInfo> productInfoList) throws IOException {

        Set<String> productStockNoSet = new HashSet<String>();

        for (ProductInfo tempProductInfo : productInfoList) {

            Product product = tempProductInfo.getProduct();

            /** 必填项是否为空 */
            if (product == null || product.getCatSeq() == null || product.getName() == null || product.getBrandSeq() == null || product.getStockNo() == null || product.getBar() == null || product.getPrice() == null) {
                return new BaseResponseMsg(false, TopConstants.MANDATORYISNULL);
            }

            /** 是否存在主图 */
            if (StringUtils.isNullOrEmpty(tempProductInfo.getProductImageData().getProductImg1())) {
                return new BaseResponseMsg(false, TopConstants.PRODUCTIMAGEISNULL);
            }

            productStockNoSet.add(product.getStockNo().trim());

        }

        /** 货号不允许重复 */
        if (productStockNoSet.size() != productInfoList.size()) {
            return new BaseResponseMsg(false, TopConstants.DATAREPEAT);
        }

        /** 验证商品sku 是否重复 */
        for (ProductInfo tempProductInfo : productInfoList) {

            Set<String> skuStrSet = getProductSKUListStr(tempProductInfo.getProductSKUAttrValues());

            List<Map<String, Object>> skuMapList = new ArrayList<Map<String, Object>>();

            if (!StringUtils.isNullOrEmpty(tempProductInfo.getProductSKUAttrValues())) {
                skuMapList = JsonUtils.parseJSON2List(tempProductInfo.getProductSKUAttrValues());
            }

            /** 如果集合Set与List 数目不同，则存在重复数据 */
            if (skuMapList.size() != skuStrSet.size()) {
                return new BaseResponseMsg(false, TopConstants.SKUREPEAT);
            }
        }
        return new BaseResponseMsg();
    }

    /**
     * <p>验证商品有效性，并添加商品</p>
     *
     * @param productInfoList 商品信息列表
     * @return
     */
    public CommonPostResult verifyAndAddProduct(List<ProductInfo> productInfoList, Long merchantID, HttpServletRequest request) {

        List<ProductFailInfo> failInfoList = new ArrayList<ProductFailInfo>();

        /** 存储存储“类别ID_属性ID”集合 */
        Set<String> skuAtrributeIdSet = new HashSet<String>();

        int totalNum = 0;
        int failNum = 0;
        int successNum = 0;

        if (productInfoList.size() > 0) {
            totalNum = productInfoList.size();
        }

        for (ProductInfo productInfo : productInfoList) {

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("catId", productInfo.getProduct().getCatSeq());
            /** sku属性 */
            param.put("type", TopConstants.SKUATTRIBUTETYPE);
            /** 种类属性状态 */
            param.put("status", TopConstants.COMMONATTRIBUTESTATUS);
            /** 种类状态 */
            param.put("statusForCatAtrr", TopConstants.COMMONATTRIBUTESTATUS);

            List<ProductCategoryAttribute> categoryAttributeList = productCategoryAttributeService.getList(param);

            /** 获取“类别ID_属性ID” Set */
            for (ProductCategoryAttribute tempProductCategoryAttribute : categoryAttributeList) {
                skuAtrributeIdSet.add(productInfo.getProduct().getCatSeq().toString() + "_" + tempProductCategoryAttribute.getId().toString());
            }
        }

        //<editor-fold desc="商品批量添加">
        for (ProductInfo productInfo : productInfoList) {

            productInfo.getProduct().setMerchantSeq(merchantID);

            try {
                /** 1. 判断商户 */
                if (merchantService.getModel(productInfo.getProduct().getMerchantSeq()) == null) {

                    ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.MERCHANTISINVALID);
                    failInfoList.add(tempProductFailInfo);
                    ++failNum;
                    continue;
                }

                /** 2. 判断品牌*/
                if (!brandRestService.existBrand(productInfo.getProduct().getMerchantSeq(), productInfo.getProduct().getBrandSeq())) {
                    ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.BRANDISINVALID);
                    failInfoList.add(tempProductFailInfo);
                    ++failNum;
                    continue;
                }

                // TODO: 16-12-13 添加商品Long[] prodInfos逻辑
                /** 3. 判断种类*/
                if (!brandCategoryService.isBrandCate(productInfo.getProduct().getMerchantSeq(), productInfo.getProduct().getBrandSeq(), productInfo.getProduct().getCatSeq())) {
                    ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.CATAGORYISINVALID);
                    failInfoList.add(tempProductFailInfo);
                    ++failNum;
                    continue;
                }

                /** 4. 判断商品货号是否重复*/
                if (this.verifyProductStockNo(productInfo.getProduct().getStockNo(), merchantID)) {
                    if (!this.addProduct(productInfo.getProduct(), productInfo.getProductAttrValues(), productInfo.getProductImageData(), productInfo.getProductDetailHtml(), merchantID, productInfo.getProdInfos())) {
                        ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.PRODUCTINFOINVALID);
                        failInfoList.add(tempProductFailInfo);
                        ++failNum;
                        continue;
                    }
                } else {
                    ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.DATAREPEAT);
                    failInfoList.add(tempProductFailInfo);
                    ++failNum;
                    continue;
                }

                /** 5. 判断商品sku是否合法 */
                if (!this.verifyProductSKUAtrributeID(productInfo.getProduct().getCatSeq().toString(), skuAtrributeIdSet, productInfo.getProductSKUAttrValues())) {
                    ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.SKUISINVALID);
                    failInfoList.add(tempProductFailInfo);
                    ++failNum;
                    continue;
                }

                /** 6. 判断sku商品是否重复*/
                if (this.verifyProductSKU(String.valueOf(productInfo.getProduct().getId()), productInfo.getProductSKUAttrValues())) {
                    this.addProductSKU(productInfo.getProduct().getId().toString(), productInfo.getProductSKUAttrValues(), request);
                } else {
                    ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.SKUREPEAT);
                    failInfoList.add(tempProductFailInfo);
                    ++failNum;
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("=== 商品添加异常 ===" + e.getMessage().toString());
                ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.ADDPRODUCTFAIL);
                failInfoList.add(tempProductFailInfo);
                ++failNum;
                continue;
            }

            /** 无异常，成功个数+1 */
            ++successNum;
        } // end for
        //</editor-fold>

        CommonPostResult commonPostResult = new CommonPostResult(totalNum, failNum, successNum, failInfoList);
        return commonPostResult;
    }

    /**
     * <p>验证商品SKU是否重复，并添加商品SKU</p>
     *
     * @param productSKUStrList 商品SKU 属性列表
     */
    public CommonPostResult verifyAndAddProductSKU(List<String> productSKUStrList, HttpServletRequest request) {

        List<ProductFailInfo> failInfoList = new ArrayList<ProductFailInfo>();

        int totalNum = 0;
        int failNum = 0;
        int successNum = 0;

        /** 更新的商品数量为基准 */
        if (productSKUStrList.size() > 0) {
            totalNum = productSKUStrList.size();
        }

        // TODO: 16-12-8 存在局限
        /** 每个productSKUAttrValues存储的是json列表，但是由于久雅service的局限，该json列表只能够存储一个对象 */
        for (String productSKUAttrValues : productSKUStrList) {

            List<Map<String, Object>> skuMapList = new ArrayList<Map<String, Object>>();

            /** 获取商品ID */
            if (!StringUtils.isNullOrEmpty(productSKUAttrValues)) {
                skuMapList = JsonUtils.parseJSON2List(productSKUAttrValues);
            }

            Long productID = Long.valueOf((String) skuMapList.get(0).get(TopConstants.SKULISTPRODNAME));

            /** 商品是否存在 */
            if (this.getProductByID(productID) == null) {
                ProductFailInfo tempProductFailInfo = new ProductFailInfo(productID.toString(), TopConstants.PRODUCTIDISINVALID);
                failInfoList.add(tempProductFailInfo);
                ++failNum;
                continue;
            }

            try {
                /** 验证商品SKU是否重复 */
                if (!verifyProductSKU(productID.toString(), productSKUAttrValues)) {
                    ProductFailInfo tempProductFailInfo = new ProductFailInfo(productID.toString(), TopConstants.SKUREPEAT);
                    failInfoList.add(tempProductFailInfo);
                    ++failNum;
                    continue;
                }

                /** 添加商品sku */
                this.addProductSKU(productID.toString(), productSKUAttrValues, request);

            } catch (Exception e) {
                e.printStackTrace();
                ProductFailInfo tempProductFailInfo = new ProductFailInfo(productID.toString(), TopConstants.ADDPRODUCTSKUFAIL);
                failInfoList.add(tempProductFailInfo);
                ++failNum;
                continue;
            }

            /** 商品sku添加成功次数 */
            ++successNum;
        }

        CommonPostResult commonPostResult = new CommonPostResult(totalNum, failNum, successNum, failInfoList);

        return commonPostResult;
    }

    /**
     * <p>验证商品有效性，并修改商品</p>
     *
     * @param productInfoList
     * @param merchantID
     * @return
     */
    public CommonPostResult verifyAndPutProduct(List<ProductInfo> productInfoList, Long merchantID) {

        List<ProductFailInfo> failInfoList = new ArrayList<ProductFailInfo>();

        int totalNum = 0;
        int failNum = 0;
        int successNum = 0;

        if (productInfoList.size() > 0) {
            totalNum = productInfoList.size();
        }

        for (ProductInfo productInfo : productInfoList) {

            if (productInfo.getProduct().getId() == null) {
                ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.PRODUCTIDISNULL);
                failInfoList.add(tempProductFailInfo);
                ++failNum;
                continue;
            }

            try {
                if (!this.verifyProductStockNo(productInfo.getProduct().getStockNo(), merchantID)) {
                    this.updateProduct(productInfo.getProduct(), productInfo.getProductAttrValues(), productInfo.getProductImageData(), productInfo.getProductDetailHtml(), merchantID);
                } else {
                    ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.STOCKNOISINVALID);
                    failInfoList.add(tempProductFailInfo);
                    ++failNum;
                    continue;
                }
            } catch (Exception e) {
                // TODO: 16-12-6 添加日志
                e.printStackTrace();
                ProductFailInfo tempProductFailInfo = new ProductFailInfo(productInfo.getProduct().getName(), TopConstants.PUTPRODUCTFAIL);
                failInfoList.add(tempProductFailInfo);
                ++failNum;
                continue;
            }
            /** 无异常，成功个数+1 */
            ++successNum;
        }

        CommonPostResult commonPostResult = new CommonPostResult(totalNum, failNum, successNum, failInfoList);
        return commonPostResult;
    }

    /**
     * <p>验证商品sku有效性，并修改商品SKU</p>
     *
     * @param productSKUInfoList 商品sku信息列表
     * @return
     */
    public CommonPostResult verifyAndPutProductSKU(List<ProductSKUInfo> productSKUInfoList) {

        List<ProductSKUFailInfo> failInfoList = new ArrayList<ProductSKUFailInfo>();

        int totalNum = 0;
        int failNum = 0;
        int successNum = 0;

        if (productSKUInfoList.size() > 0) {
            totalNum = productSKUInfoList.size();
        }

        for (ProductSKUInfo productSKUInfo : productSKUInfoList) {

            if (productSKUInfo.getProductSKU().getId() == null) {
                ProductSKUFailInfo tempProductSKUFailInfo = new ProductSKUFailInfo(TopConstants.PRODUCTSKUIDISNULL, TopConstants.PRODUCTSKUIDISNULL);
                failInfoList.add(tempProductSKUFailInfo);
                ++failNum;
                continue;
            }
            ProductSKU targetEntity = productSKUService.getModel(productSKUInfo.getProductSKU().getId());

            if (targetEntity == null) {
                ProductSKUFailInfo tempProductSKUFailInfo = new ProductSKUFailInfo(productSKUInfo.getProductSKU().getId().toString(), TopConstants.SKUISINVALID);
                failInfoList.add(tempProductSKUFailInfo);
                ++failNum;
                continue;
            }

            BeanUtils.copyProperties(productSKUInfo.getProductSKU(), targetEntity);
            targetEntity.setUpdateDate(new Date());
            try {
                productSKUService.update(targetEntity);
            } catch (CoreException e) {
                // TODO: 16-12-9 添加日志
                e.printStackTrace();
                ProductSKUFailInfo tempProductSKUFailInfo = new ProductSKUFailInfo(productSKUInfo.getProductSKU().getId().toString(), TopConstants.PUTPRODUCTSKUFAIL);
                failInfoList.add(tempProductSKUFailInfo);
                ++failNum;
                continue;
            }

            /** 成功数量+1 */
            ++successNum;
        }// end for

        CommonPostResult commonPostResult = new CommonPostResult(totalNum, failNum, successNum, failInfoList);
        return commonPostResult;
    }

    /**
     * 验证商品的skuID
     *
     * @param productCatagory      商品类别ID
     * @param skuAtrributeIdSet    sku属性ID集合
     * @param productSKUAttrValues 商品sku列表json串
     */
    public Boolean verifyProductSKUAtrributeID(String productCatagory, Set<String> skuAtrributeIdSet, String productSKUAttrValues) {
        List<Map<String, Object>> skuMapList = new ArrayList<Map<String, Object>>();

        if (!StringUtils.isNullOrEmpty(productSKUAttrValues)) {
            skuMapList = JsonUtils.parseJSON2List(productSKUAttrValues);
        }

        for (Map<String, Object> tempMap : skuMapList) {

            List<Map<String, Object>> skuAttriValues = (List<Map<String, Object>>) tempMap.get(TopConstants.SKULISTATRRINAME);

            if (CollectionUtils.isNotEmpty(skuAttriValues)) {

                /** 验证该属性ID是否存在 */
                for (Map<String, Object> tempSKU : skuAttriValues) {
                    if (!skuAtrributeIdSet.contains(productCatagory + "_" + tempSKU.get(TopConstants.SKUJSONVALUEATRRIID).toString())) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    /**
     * 拼接一个商品 skuList 属性值组成的<p>不重复</p>字符串列表
     *
     * @param productSKUAttrValues 商品sku列表json串
     * @return 拼接串Set
     * @throws IOException
     */
    public Set<String> getProductSKUListStr(String productSKUAttrValues) throws IOException {

        Set<String> skuStrSet = new HashSet<String>();

        List<Map<String, Object>> skuMapList = new ArrayList<Map<String, Object>>();

        if (!StringUtils.isNullOrEmpty(productSKUAttrValues)) {
            skuMapList = JsonUtils.parseJSON2List(productSKUAttrValues);
        }

        for (Map<String, Object> tempMap : skuMapList) {

            List<Map<String, Object>> skuAttriValues = (List<Map<String, Object>>) tempMap.get(TopConstants.SKULISTATRRINAME);

            Map<String, String> skuValueMap = new HashedMap();

            /** 获取sku value值 */
            if (CollectionUtils.isNotEmpty(skuAttriValues)) {
                StringBuffer skuStr = new StringBuffer();

                /** 根据 attrID:Value 组装sku */
                for (Map<String, Object> tempSKU : skuAttriValues) {
                    skuValueMap.put((String) tempSKU.get(TopConstants.SKUJSONVALUEATRRIID), tempSKU.get(TopConstants.SKUJSONVALUEATRRIVALUE).toString().trim());
                }

                /** 根据attrID排序 */
                Map<String, String> treeSKUMap = new TreeMap<String, String>(skuValueMap);

                /** 拼接属性值串 */
                for (Map.Entry<String, String> temEntry : treeSKUMap.entrySet()) {
                    skuStr.append(temEntry.getValue());
                    skuStr.append(",");
                }

                /** 去除尾部逗号 */
                skuStrSet.add(skuStr.substring(0, skuStr.length() - 1));
            } else {
                /** sku值为空，说明没有设值，也要去重处理 */
                skuStrSet.add(TopConstants.EMPTYSTRING);
            }

        }
        return skuStrSet;
    }
}
