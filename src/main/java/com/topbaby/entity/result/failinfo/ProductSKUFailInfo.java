package com.topbaby.entity.result.failinfo;

/**
 * details：商品SKU添加失败信息基本实体
 * Created by sxy on 16-12-9.
 */
public class ProductSKUFailInfo {

    /**
     * 失败商品名称
     */
    private String skuID;
    /**
     * 失败原因
     */
    private String reason;

    public ProductSKUFailInfo(String skuID, String reason) {
        this.skuID = skuID;
        this.reason = reason;
    }

    public ProductSKUFailInfo() {
    }

    public String getSkuID() {
        return skuID;
    }

    public void setSkuID(String skuID) {
        this.skuID = skuID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
