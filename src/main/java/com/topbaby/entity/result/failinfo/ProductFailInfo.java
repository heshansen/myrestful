package com.topbaby.entity.result.failinfo;

/**
 * details： 商品验证失败、添加失败等失败信息基本实体
 * Created by sxy on 16-12-7.
 */
public class ProductFailInfo {
    /**
     * 失败商品名称
     */
    private String name;
    /**
     * 失败原因
     */
    private String reason;

    public ProductFailInfo(String name, String reason) {
        this.name = name;
        this.reason = reason;
    }

    public ProductFailInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
