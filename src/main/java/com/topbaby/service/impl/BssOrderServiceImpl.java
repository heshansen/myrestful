package com.topbaby.service.impl;

import com.topbaby.ecommerce.core.Constants;
import com.topbaby.ecommerce.order.entity.Order;
import com.topbaby.ecommerce.order.service.OrderService;
import com.topbaby.service.BssOrderService;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: 门店订单管理 </p>
 *
 * @Author heshansen
 * @Date 2016-12-23 14:16.
 * @Version V1.0
 */
@Service
public class BssOrderServiceImpl implements BssOrderService{
        @Autowired
        private OrderService orderService;

    /**
     * 获取门店订单列表
     */
    public PageModel<Order> getPageList(Map<String, Object> param) throws Exception {
        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put("brandshopSeq", param.get("brandshopId"));
        param1.put("deliveryType", "1");// 自提
        param1.put("splited", false);// 去除拆单后的主订单
        param1.put("status", param.get("status"));
        param1.put("orderIdVague", param.get("orderId"));// 订单编号模糊查询
        param1.put(Constants.ORDERRULES, Constants.ORDERRULES);

        return orderService.getPageList(param1, (PageQueryModel) param.get("pageQueryModel"));
    }
}
