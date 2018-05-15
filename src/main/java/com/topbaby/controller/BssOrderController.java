package com.topbaby.controller;

import com.topbaby.ecommerce.order.entity.Order;
import com.topbaby.entity.base.BaseParam;
import com.topbaby.service.BssOrderService;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.esbuilder.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.topbaby.common.TopConstants;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssOrderRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.timon.security.authz.IAuthorizationService;

/**
 * <p>门店订单管理类</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-16
 */

@Controller
@RequestMapping("/restful/bss/order")
public class BssOrderController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BssOrderRestService bssOrderRestService;

    @Autowired
    private IAuthorizationService authorizationService;
    @Autowired
    private BssOrderService bssOrderService;


    /**
     * 订单列表
     * 1.支持多状态订单查询
     * 2.支持订单编号模糊查询
     *
     * @param status         　订单状态（可选）
     * @param orderId        　订单编号（可选）
     * @param pageQueryModel 　分页参数
     * @return
     */
    // TODO: 16-12-21 get
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ResponseBody
    public Object getList(@RequestParam(value = "status", required = false) String status,
                          @RequestParam(value = "orderId", required = false) String orderId,
                          PageQueryModel pageQueryModel) {
//        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Long brandshopId = Long.valueOf(254);//默认值
        System.out.println("订单列表查询开始：brandshopId＝" + brandshopId + ",status=" + status + ",orderId=" + orderId);
        // 门店只能处理自提订单
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("brandshopId", brandshopId);
        param.put("status", status);
        param.put("orderId", StringUtils.trim(orderId));// 订单编号模糊查询
        param.put("pageQueryModel", pageQueryModel);
        PageModel<Order> pageModel;
        try {
            pageModel = bssOrderService.getPageList(param);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false, "订单查询失败！");
        }
        return pageModel;
    }
    /**
     * <p>确认会员自提订单（收货确认）</p>
     *
     * @param id 订单主键ID
     * @return BaseResponseMsg
     */
    @RequestMapping(value = "/confirmOrder",method = {RequestMethod.GET})
    @ResponseBody
    public BaseResponseMsg confirmOrder(@RequestParam(value = "id", required = false, defaultValue = "114") Long id) {

        if (id == null) {
            return new BaseResponseMsg(false, TopConstants.IDISNULL);
        }

        try {
            return bssOrderRestService.confirmOrderReceived(id);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.ORDERRECEIVEDERROR);
        }
    }

    /**
     * <p>自提订单(会员)收货</p>
     *
     * @param jsonBody 订单主键对象
     * @return BaseResponseMsg
     */
    @RequestMapping(value = "/orderReceived",method = {RequestMethod.POST,RequestMethod.PUT})
    @Transactional
    @ResponseBody
    public BaseResponseMsg orderReceived(@RequestBody BaseParam jsonBody) {

        if (jsonBody.getId() == null) {
            return new BaseResponseMsg(false, TopConstants.IDISNULL);
        }

        BrandshopUser brandshopUser = (BrandshopUser) authorizationService.getAuthenticatedUser();

        try {
            return bssOrderRestService.orderReceived(jsonBody.getId() , brandshopUser);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.ORDERRETURNERROR);
        }
    }
}
