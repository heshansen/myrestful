package com.topbaby.entity;

import com.topbaby.ecommerce.order.entity.MemberOrderReturn;
import com.topbaby.ecommerce.order.entity.Order;
import com.topbaby.ecommerce.order.entity.OrderItem;
import com.topbaby.entity.base.BaseResponseMsg;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>退货订单详情类</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-20
 */
public class OrderReturnDetailInfo extends BaseResponseMsg {
    private List imageList;
    private OrderItem orderItem;
    private List<Map<String, Object>> skuList;
    private Order order;
    private MemberOrderReturn memberOrderReturn;
    private BigDecimal returnAmount;

    public OrderReturnDetailInfo() {
        super();
    }

    public OrderReturnDetailInfo(List imageList, OrderItem orderItem, List<Map<String, Object>> skuList, Order order, MemberOrderReturn memberOrderReturn, BigDecimal returnAmount) {
        this.imageList = imageList;
        this.orderItem = orderItem;
        this.skuList = skuList;
        this.order = order;
        this.memberOrderReturn = memberOrderReturn;
        this.returnAmount = returnAmount;
    }

    public List getImageList() {
        return imageList;
    }

    public void setImageList(List imageList) {
        this.imageList = imageList;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public List<Map<String, Object>> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Map<String, Object>> skuList) {
        this.skuList = skuList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MemberOrderReturn getMemberOrderReturn() {
        return memberOrderReturn;
    }

    public void setMemberOrderReturn(MemberOrderReturn memberOrderReturn) {
        this.memberOrderReturn = memberOrderReturn;
    }

    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }
}
