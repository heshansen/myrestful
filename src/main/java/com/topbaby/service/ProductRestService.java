package com.topbaby.service;

import com.topbaby.ecommerce.product.entity.Product;
import com.topbaby.ecommerce.product.entity.ProductImageData;
import com.topbaby.entity.ProductInfo;
import com.topbaby.entity.param.ProductPageParam;
import com.topbaby.entity.ProductSKUInfo;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.entity.result.CommonPostResult;
import org.esbuilder.business.model.IPageQueryModel;
import org.esbuilder.business.model.PageModel;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * details：商品 restful service 服务类
 * Created by sxy on 16-11-29.
 */
public interface ProductRestService {
    /**
     * 验证商品货号（商品）是否唯一
     */
    Boolean verifyProductStockNo(String stockNo, long merchantID) throws Exception;

    /**
     * 验证商品SKU是否重复
     */
    Boolean verifyProductSKU(String productID, String productSKUAttrValues) throws Exception;

    /**
     * 添加商品sku
     */
    Boolean addProductSKU(String productID, String productSKUAttrValues, HttpServletRequest request) throws Exception;

    /**
     * 添加商品
     */
    Boolean addProduct(Product product, String productAttrValues, ProductImageData productImageData, String productDetailHtml, long merchantID, Long[] prodInfos) throws Exception;

    /**
     * 根据ID获取商品信息
     */
    Product getProductByID(long productID) throws Exception;

    /**
     * 更新商品信息
     */
    Boolean updateProduct(Product product, String productAttrValues, ProductImageData productImageData, String productDetailHtml, long merchantID) throws Exception;

    /**
     * 删除商品信息
     */
    Boolean cancelProductByID(long productID) throws Exception;

    /**
     * 获取商品分页数据
     */
    PageModel<Product> getProductPageList(ProductPageParam productPageParam, IPageQueryModel pageQueryModel) throws Exception;

    /**
     * 验证添加商品时，商品信息对象（ProductInfo）中是否包含重复数据、以及数据是否有效
     */
    BaseResponseMsg verifyProductInfo(List<ProductInfo> productInfoList) throws IOException;

    /**
     * 验证商品有效性，并添加商品
     */
    CommonPostResult verifyAndAddProduct(List<ProductInfo> productInfoList, Long merchantID, HttpServletRequest request) throws Exception;

    /**
     * 验证商品SKU是否重复，并添加商品SKU
     */
    CommonPostResult verifyAndAddProductSKU(List<String> productSKUStrList, HttpServletRequest request) throws Exception;

    /**
     * 验证商品有效性，并修改商品信息
     */
    CommonPostResult verifyAndPutProduct(List<ProductInfo> productInfoList, Long merchantID) throws Exception;

    /**
     * 验证商品sku有效性，并修改商品SKU
     */
    CommonPostResult verifyAndPutProductSKU(List<ProductSKUInfo> productSKUInfoList) throws Exception;

    /**
     * 验证商品skuID是否合法
     */
    Boolean verifyProductSKUAtrributeID(String peoductCatagory, Set<String> skuAtrributeIdSet, String productSKUAttrValues) throws Exception;

}
