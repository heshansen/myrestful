package com.topbaby.service;

import com.topbaby.ecommerce.order.entity.Order;
import org.esbuilder.business.model.PageModel;

import java.util.Map;

/**
 * <p>Title: 门店订单管理</p>
 *
 * @Author heshansen
 * @Date 2016-12-23 14:15.
 * @Version V1.0
 */
public interface BssOrderService {
    /**
     * 获取门店订单列表信息
     */
    PageModel<Order> getPageList(Map<String, Object> param) throws Exception;
}
