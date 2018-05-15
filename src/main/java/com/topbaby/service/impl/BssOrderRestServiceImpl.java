package com.topbaby.service.impl;

import com.topbaby.common.TopConstants;
import com.topbaby.ecommerce.brandshop.entity.BrandshopStock;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.service.BrandshopStockService;
import com.topbaby.ecommerce.order.entity.*;
import com.topbaby.ecommerce.order.service.*;
import com.topbaby.ecommerce.product.entity.Product;
import com.topbaby.ecommerce.product.service.ProductSKUAttributeValueService;
import com.topbaby.ecommerce.product.service.ProductService;
import com.topbaby.entity.OrderConfirmInfo;
import com.topbaby.entity.OrderItemProduct;
import com.topbaby.entity.OrderReturnDetailInfo;
import com.topbaby.entity.OrderReturnInfo;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssOrderRestService;
import org.apache.commons.collections.CollectionUtils;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.esbuilder.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>BSS 订单处理实现类</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-19
 */
@Service
public class BssOrderRestServiceImpl implements BssOrderRestService {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSKUAttributeValueService productSKUAttributeValueService;
    @Autowired
    private IntegralInterfaceService integralInterfaceService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberOrderReturnService memberOrderReturnService;
    @Autowired
    private BrandshopStockService brandshopStockService;
    @Autowired
    private OrderReturnService orderReturnService;

    /**
     * 用户提货确认（获取订单信息）
     *
     * @param id 订单主键id
     * @return BaseResponseMsg
     */
    public BaseResponseMsg confirmOrderReceived(Long id) throws CoreException {

        Order order = orderService.getModel(id);

        if (order == null) {
            return new BaseResponseMsg(false, TopConstants.ORDERISINVALID);
        }

        /** 获取关联的订单子项 */
        List<OrderItemRelation> orderItemRelation = orderService.getListByOrderSeq(order.getId());
        if (orderItemRelation == null) {
            return new BaseResponseMsg(false, TopConstants.ORDERITEMISINVALID);
        }

        List<OrderItemProduct> orderItemProductList = new ArrayList<OrderItemProduct>();

        for (OrderItemRelation tempOrderItemRelation : orderItemRelation) {

            OrderItemProduct orderItemProduct = new OrderItemProduct();
            OrderItem orderItem = orderItemService.getModel(tempOrderItemRelation.getOrderItemSeq());
            Product product = this.productService.getModel(orderItem.getProdSeq());
            orderItemProduct.setProduct(product);
            orderItemProduct.setNumber(orderItem.getNumber());

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("brshopSeq", order.getBrandshopSeq());
            params.put("productSeq", orderItem.getProdSeq());
            params.put("skuSeq", orderItem.getSkuSeq());
            /** 获取商品信息 */
            BrandshopStock brandshopStock;
            brandshopStock = brandshopStockService.getModelByProductSku(params);

            /** 设置商品淘璞价 */
            if (brandshopStock != null) {
                orderItemProduct.setUnitPrice((brandshopStockService.getProductUnitPrice(product, brandshopStock, order.getDeliveryType())));
            }

            orderItemProduct.setSkuAttributeValueList(productSKUAttributeValueService.getList(params));

            orderItemProductList.add(orderItemProduct);
        }

        OrderConfirmInfo orderConfirmInfo = new OrderConfirmInfo();

        orderConfirmInfo.setOrderID(order.getOrderId());
        orderConfirmInfo.setItemList(orderItemProductList);

        return orderConfirmInfo;
    }

    /**
     * 用户收货(自提收货)
     *
     * @param id 订单主键id
     * @return BaseResponseMsg
     */
    public BaseResponseMsg orderReceived(Long id, BrandshopUser brandshopUser) throws CoreException {

        Order order = orderService.getModel(id);

        if (brandshopUser != null && order != null) {
            /** 判断导购员是否属于该门店*/
            if (brandshopUser.getBrandshopSeq().longValue() != order.getBrandshopSeq().longValue()) {
                return new BaseResponseMsg(false, TopConstants.BRANDSHOPUSERINVALID);
            }
        } else {
            return new BaseResponseMsg(false, TopConstants.ORDERORUSERINVALID);
        }

        if (order.getStatus().equals(OrderStatus.RECEIVED.getCode())) {
            return new BaseResponseMsg(false, "该订单已于" + order.getReceiptTime() + "确认收货");
        } else if (order.getStatus().equals(OrderStatus.INCANCEL.getCode())) {
            return new BaseResponseMsg(false, TopConstants.ORDERISCANCELING);
        } else if (order.getStatus().equals(OrderStatus.CANCELED.getCode())) {
            return new BaseResponseMsg(false, TopConstants.ORDERISCANCELED);
        } else if (order.getStatus().equals(OrderStatus.FINISHED.getCode())) {
            return new BaseResponseMsg(false, TopConstants.ORDERISDONE);
        } else {

            order.setStatus(OrderStatus.RECEIVED.getCode());
            order.setReceiptTime(new Timestamp(new Date().getTime()));
            /** 设置导购员处理的订单 */
            order.setConsigner(brandshopUser.getId());
            orderService.updateOrder(order);
        }

        return new BaseResponseMsg();
    }

    /**
     * 获取门店退货订单列表
     *
     * @param status 订单状态（0-申请审核中，1-申请已同意,2-商家已收货，3-申请已完成，4-申请拒绝）
     * @return BaseResponseMsg
     */
    public BaseResponseMsg getOrderReturnList(String status, Long brandshopID, PageQueryModel pageQueryModel) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("brandshopSeq", brandshopID);
        param.put("status", status);
        param.put("deliveryType", TopConstants.ONE);// 导购员只处理自提订单的退货
        param.put(TopConstants.ORDERRULES, TopConstants.ORDERRULES);// 订单降序排列
        PageModel<MemberOrderReturn> pageModel;
        pageModel = memberOrderReturnService.getPageListForManage(pageQueryModel,param);

        if (CollectionUtils.isEmpty(pageModel.getDataList())) {
            return new BaseResponseMsg(false, TopConstants.DATAISNULL);
        }

        OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
        orderReturnInfo.setPageModel(pageModel);

        return orderReturnInfo;
    }

    /**
     * 获取退货订单详情
     *
     * @param orderReturnID 退货订单ID
     */
    public BaseResponseMsg getOrderReturnDetails(Long orderReturnID) throws CoreException {

        OrderReturnDetailInfo orderReturnDetailInfo = new OrderReturnDetailInfo();

        MemberOrderReturn memberOrderReturn = memberOrderReturnService.getModel(orderReturnID);

        Map<String, Object> result;

        /** 退货订单积分抵用金额、支付金额、优惠金额 */
        result = integralInterfaceService.getMemberOrderReturnPaymentInfo(memberOrderReturn);

        BigDecimal returnAmount = BigDecimal.ZERO;
        if (result.get("payAmount") != null) {
            returnAmount = new BigDecimal(result.get("payAmount").toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        OrderItem orderItem = null;
        Order order = null;
        List<Map<String, Object>> skuList = null;

        /** 获取退货订单关联的订单信息 */
        if (memberOrderReturn != null) {
            order = orderService.getModel(memberOrderReturn.getOrderSeq());
            orderItem = orderItemService.getModel(memberOrderReturn.getOrderItemSeq());
            Product product = productService.getModel(orderItem.getProdSeq());
            orderItem.setProduct(product);

            /**获得商品sku属性值*/
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("skuSeq", orderItem.getSkuSeq());
            skuList = productSKUAttributeValueService.getSkuValueById(params);
        }
        String imagesArray = memberOrderReturn.getImageIds();
        if (!StringUtils.isEmpty(imagesArray)) {
            orderReturnDetailInfo.setImageList(Arrays.asList(imagesArray.split(",")));
        }

        orderReturnDetailInfo.setOrder(order);
        orderReturnDetailInfo.setSkuList(skuList);
        orderReturnDetailInfo.setOrderItem(orderItem);

        /** 退货订单商品信息 */
        orderReturnDetailInfo.setMemberOrderReturn(memberOrderReturn);
        orderReturnDetailInfo.setReturnAmount(returnAmount);

        return orderReturnDetailInfo;
    }

    /**
     * 审核退货订单
     *
     * @param orderReturnID 退货订单ID
     * @param sendAddress   门店收货地址
     * @param status        退货订单状态
     */
    public BaseResponseMsg verifyOrderReturn(Long orderReturnID, String status, String sendAddress) throws CoreException {

        if (orderReturnID == null) {
            return new BaseResponseMsg(false, TopConstants.VERIFYERROR);
        }

        MemberOrderReturn memberOrderReturn = this.memberOrderReturnService.getModel(orderReturnID);

        memberOrderReturn.setReturnAddress(sendAddress);
        memberOrderReturn.setStatus(status);

        if (status != null && status.equals(TopConstants.FOUR)) {

            this.orderReturnService.verifyRefuseOrderReturnProcessor(memberOrderReturn);
        } else if (status != null && status.equals(TopConstants.ONE)) {
            this.memberOrderReturnService.update(memberOrderReturn);
        }


        return new BaseResponseMsg();
    }

    /**
     * 商家(门店)已收货/客户已退货
     *
     * @param orderReturnID
     */
    public BaseResponseMsg receiveOrderReturn(Long orderReturnID) throws CoreException {

        MemberOrderReturn memberOrderReturn = memberOrderReturnService.getModel(orderReturnID);
        if (!TopConstants.ONE.equals(memberOrderReturn.getStatus())) {
            return new BaseResponseMsg(false, TopConstants.ORDERISUNHANDLE);
        }

        /** 设置为状态为 已收货 */
        memberOrderReturn.setStatus(TopConstants.TWO);
        memberOrderReturnService.confirmReceiving(memberOrderReturn);

        return new BaseResponseMsg();
    }
}
