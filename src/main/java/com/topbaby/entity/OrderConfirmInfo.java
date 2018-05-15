package com.topbaby.entity;

import com.topbaby.entity.base.BaseResponseMsg;

import java.util.List;

/**
 * <p>订单信息</p>
 * details：
 * 存储用户自提时的订单信息
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-16
 */
public class OrderConfirmInfo extends BaseResponseMsg {
    /**
     * 订单子项信息
     */
    private List<OrderItemProduct> itemList;
    /**
     * 订单编号
     */
    private String orderID;

    public OrderConfirmInfo() {
        super();
    }

    public OrderConfirmInfo(List<OrderItemProduct> itemList, String orderID) {
        this.itemList = itemList;
        this.orderID = orderID;
    }

    public List<OrderItemProduct> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItemProduct> itemList) {
        this.itemList = itemList;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
