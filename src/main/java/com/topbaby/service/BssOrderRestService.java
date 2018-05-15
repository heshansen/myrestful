package com.topbaby.service;

import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.entity.base.BaseResponseMsg;
import org.esbuilder.business.model.PageQueryModel;

/**
 * <p>BSS 订单处理服务类</p>
 * details：
 * 订单查看、退货、收货等
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-19
 */
public interface BssOrderRestService {
    /**
     * 用户收货确认（获取订单信息）
     */
    BaseResponseMsg confirmOrderReceived(Long id) throws Exception;

    /**
     * 用户收货(自提收货)
     */
    BaseResponseMsg orderReceived(Long id, BrandshopUser brandshopUser) throws Exception;

    /**
     * 获取退货订单列表
     */
    BaseResponseMsg getOrderReturnList(String status, Long brandshopID, PageQueryModel pageQueryModel) throws Exception;

    /**
     * 获取退货订单详情
     */
    BaseResponseMsg getOrderReturnDetails(Long orderReturnID) throws Exception;

    /**
     * 审核退货订单
     */
    BaseResponseMsg verifyOrderReturn(Long id, String status, String sendAddress) throws Exception;

    /**
     * 商家(门店)已收货/客户已退货
     */
    BaseResponseMsg receiveOrderReturn(Long orderReturnID) throws Exception;
}
