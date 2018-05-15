package com.topbaby.entity;

import com.topbaby.ecommerce.product.entity.Product;
import com.topbaby.ecommerce.product.entity.ProductImageData;

/**
 * details： 商品信息实体,包含商品基本信息,基本属性列表,sku属性列表,以及图片信息
 * Created by sxy on 16-12-5.
 */
public class ProductInfo {

    /**
     * 商品对象
     */
    private Product product;
    /**
     * 主图1、2、3、4、5的ID + 名称
     */
    private ProductImageData productImageData;
    /**
     * 商品基本属性json串
     */
    private String productAttrValues;
    /**
     * 商品详情图片（一张）字节流
     */
    private String productDetailHtml;
    /**
     * 商品SKU json串
     * example:[{"prodSeq":"78","price":"759.00","bar":"83C501","attrData":[{"id":"119","value":"橙色"},{"id":"311","value":"80（1-2岁）"}]},{"prodSeq":"78","price":"759.00","bar":"83C501","attrData":[{"id":"119","value":"橙色"},{"id":"311","value":"90（2-3岁）"}]}]
     */
    private String productSKUAttrValues;

    /**
     * 身份证类型（1-身份证，2-港澳通行证，3-护照） 仅prodType = 1 有效
     */
    private Long[] prodInfos;

    public Long[] getProdInfos() {
        return prodInfos;
    }

    public void setProdInfos(Long[] prodInfos) {
        this.prodInfos = prodInfos;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductImageData getProductImageData() {
        return productImageData;
    }

    public void setProductImageData(ProductImageData productImageData) {
        this.productImageData = productImageData;
    }

    public String getProductAttrValues() {
        return productAttrValues;
    }

    public void setProductAttrValues(String productAttrValues) {
        this.productAttrValues = productAttrValues;
    }

    public String getProductDetailHtml() {
        return productDetailHtml;
    }

    public void setProductDetailHtml(String productDetailHtml) {
        this.productDetailHtml = productDetailHtml;
    }

    public String getProductSKUAttrValues() {
        return productSKUAttrValues;
    }

    public void setProductSKUAttrValues(String productSKUAttrValues) {
        this.productSKUAttrValues = productSKUAttrValues;
    }
}
