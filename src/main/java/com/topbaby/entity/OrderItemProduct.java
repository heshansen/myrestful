package com.topbaby.entity;

import com.topbaby.ecommerce.product.entity.Product;
import com.topbaby.ecommerce.product.entity.ProductSKUAttributeValue;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>订单item实体类</p>
 * details：
 * 存储订单商品、商品数量信息
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-19
 */
public class OrderItemProduct {
    /**
     * 商品信息
     */
    private Product product;
    /**
     * 商品sku信息列表
     */
    private List<ProductSKUAttributeValue> skuAttributeValueList;
    /**
     * 商品淘璞价
     */
    private BigDecimal unitPrice;
    /**
     * 商品数量
     */
    private int number;

    public OrderItemProduct() {
    }

    public OrderItemProduct(Product product, List<ProductSKUAttributeValue> skuAttributeValueList, BigDecimal unitPrice, int number) {
        this.product = product;
        this.skuAttributeValueList = skuAttributeValueList;
        this.unitPrice = unitPrice;
        this.number = number;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductSKUAttributeValue> getSkuAttributeValueList() {
        return skuAttributeValueList;
    }

    public void setSkuAttributeValueList(List<ProductSKUAttributeValue> skuAttributeValueList) {
        this.skuAttributeValueList = skuAttributeValueList;
    }
}
