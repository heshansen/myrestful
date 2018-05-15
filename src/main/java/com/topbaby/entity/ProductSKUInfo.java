package com.topbaby.entity;

import com.topbaby.ecommerce.product.entity.ProductSKU;

/**
 * details： 商品SKU实体
 * Created by sxy on 16-12-9.
 */
public class ProductSKUInfo {
    /**
     * 商品sku信息
     */
    private ProductSKU productSKU;
    /**
     * 属性图片文件（暂时不使用）
     */
    private String newAttrFile;
    /**
     * 商品属性值ID 关联到具体的属性值，比如红色需要图片，橙色需要图片（见MosProductSKUAction）(emall_products_attributeValues.productAttrValSeq) (暂时不用)
     */
    private Long productAttrValueID;

    public ProductSKU getProductSKU() {
        return productSKU;
    }

    public void setProductSKU(ProductSKU productSKU) {
        this.productSKU = productSKU;
    }

    public String getNewAttrFile() {
        return newAttrFile;
    }

    public void setNewAttrFile(String newAttrFile) {
        this.newAttrFile = newAttrFile;
    }

    public Long getProductAttrValueID() {
        return productAttrValueID;
    }

    public void setProductAttrValueID(Long productAttrValueID) {
        this.productAttrValueID = productAttrValueID;
    }
}
