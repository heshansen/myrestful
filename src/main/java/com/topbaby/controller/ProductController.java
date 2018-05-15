package com.topbaby.controller;

import com.topbaby.common.TopConstants;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.product.entity.Product;
import com.topbaby.entity.ProductInfo;
import com.topbaby.entity.param.ProductPageParam;
import com.topbaby.entity.ProductSKUInfo;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.ProductRestService;
import org.apache.commons.collections.CollectionUtils;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.timon.security.authz.IAuthorizationService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * details：商品 restful 接口
 * Created by sxy on 16-11-25.
 */
@Controller
@RequestMapping("/restful")
public class ProductController {
    @Resource
    ProductRestService productRestService;

    @Autowired
    private IAuthorizationService authorizationService;


    /**
     * <p>获取商品</p>
     *
     * @param productID 商品ID
     * @return 商品信息Json串
     */
    @RequestMapping(value = "/getProduct", method = RequestMethod.GET)
    @ResponseBody
    public Object getProduct(HttpServletRequest request,
                             @RequestParam(value = "productID",
                                     defaultValue = "11",
                                     required = true
                             ) String productID) {

        BrandshopUser brandshopUser = authorizationService.getAuthenticatedUser(BrandshopUser.class);

        Product product;
        try {
            product = productRestService.getProductByID(Long.parseLong(productID.trim()));
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.GETINFOFAIL);
        }

        if (product == null) {
            return new BaseResponseMsg(false, TopConstants.GETINFOFAIL);
        }

        return product;
    }

    /**
     * <p>获取商品分页数据</p>
     *
     * @param pageQueryModel   分页数据信息
     * @param productPageParam 筛选参数信息
     * @return 分页数据
     */
    @RequestMapping(value = "/getProductPageList", method = RequestMethod.GET)
    @ResponseBody
    public Object getProductPageList(PageQueryModel pageQueryModel, ProductPageParam productPageParam) {
        PageModel<Product> pageModel;
        try {
            pageModel = productRestService.getProductPageList(productPageParam, pageQueryModel);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.GETINFOFAIL);
        }

        return pageModel;

    }

    /**
     * <p>添加商品</p>
     *
     * @param productInfoList 商品信息列表
     */
    @RequestMapping(value = "/postProduct")
    @Transactional
    @ResponseBody
    public BaseResponseMsg postProduct(@RequestBody List<ProductInfo> productInfoList, HttpServletRequest request) {

        BaseResponseMsg baseResponseMsg;

        /** 验证数据是否为空 */
        if (CollectionUtils.isEmpty(productInfoList)) {
            return new BaseResponseMsg(false, TopConstants.DATAISNULL);
        }

        /** 判断 ProductInfo 合法性（必填项+数据是否重复） */
        try {

            baseResponseMsg = productRestService.verifyProductInfo(productInfoList);
            if (!baseResponseMsg.getResult()) {
                return baseResponseMsg;
            }
        } catch (IOException e) {
            return new BaseResponseMsg(false, TopConstants.FAILINFO);

        }

        /** 通过登录用户,获取商户ID */
        BrandshopUser brandshopUser = (BrandshopUser) authorizationService.getAuthenticatedUser();

        long merchantID = brandshopUser.getBrandshop().getMerchantSeq();

        /** 添加商品 */
        try {
            baseResponseMsg = productRestService.verifyAndAddProduct(productInfoList, merchantID, request);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.FAILINFO);
        }

        return baseResponseMsg;
    }

    /**
     * <p>修改商品信息</p>
     *
     * @param productInfoList 商品信息列表
     * @return BaseResponseMsg
     */
    @RequestMapping(value = "/putProduct", method = RequestMethod.PUT)
    @Transactional
    @ResponseBody
    public BaseResponseMsg putProduct(@RequestBody List<ProductInfo> productInfoList) {
        /** 通过登录用户,获取商户ID */
        BrandshopUser brandshopUser = (BrandshopUser) authorizationService.getAuthenticatedUser();
        long merchantID = brandshopUser.getBrandshop().getMerchantSeq();


        if (productInfoList == null || CollectionUtils.isEmpty(productInfoList)) {
            return new BaseResponseMsg(false, TopConstants.DATAISNULL);
        }

        try {
            return productRestService.verifyAndPutProduct(productInfoList, merchantID);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.FAILINFO);
        }
    }

    /**
     * <p>添加商品sku</p>
     *
     * @param productSKUStrList 示例：["[{\"prodSeq\":\"11\",\"price\":\"666.00\",\"bar\":\"5094121\",\"attrData\":[{\"id\":\"795\",\"value\":\"哈哈\"},{\"id\":\"796\",\"value\":\"20mm\"}]}]"]
     * @return BaseResponseMsg
     */
    @RequestMapping(value = "/postProductSKU", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public BaseResponseMsg postProductSKU(@RequestBody List<String> productSKUStrList, HttpServletRequest request) {

        if (productSKUStrList == null || CollectionUtils.isEmpty(productSKUStrList)) {
            return new BaseResponseMsg(false, TopConstants.DATAISNULL);
        }

        try {
            return productRestService.verifyAndAddProductSKU(productSKUStrList, request);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.FAILINFO);
        }
    }

    /**
     * <p>修改商品SKU</p>
     *
     * @param productSKUInfoList 商品sku列表 示例：["[{\"prodSeq\":\"11\",\"price\":\"666.00\",\"bar\":\"5094121\",\"attrData\":[{\"id\":\"795\",\"value\":\"哈哈\"},{\"id\":\"796\",\"value\":\"20mm\"}]}]"]
     * @return BaseResponseMsg
     */
    @RequestMapping(value = "/putProductSKU", method = RequestMethod.PUT)
    @Transactional
    @ResponseBody
    public BaseResponseMsg putProductSKU(@RequestBody List<ProductSKUInfo> productSKUInfoList) {

        if (productSKUInfoList == null || CollectionUtils.isEmpty(productSKUInfoList)) {
            return new BaseResponseMsg(false, TopConstants.DATAISNULL);
        }

        try {
            return productRestService.verifyAndPutProductSKU(productSKUInfoList);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.FAILINFO);
        }
    }
}
