package com.topbaby.controller;

import com.topbaby.common.TopConstants;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssOrderRestService;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.timon.security.authz.IAuthorizationService;

/**
 * <p>门店订单退货类</p>
 * details：
 * 门店退货管理、审核等
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-20
 */
@Controller
@RequestMapping("/restful/bss/order/return")
public class BssOrderReturnController {

    @Autowired
    BssOrderRestService bssOrderRestService;

    @Autowired
    private IAuthorizationService authorizationService;

    /**
     * <p>获取门店退货订单列表</p>
     *
     * @param pageQueryModel 分页数据
     * @param status 退货订单状态（0-申请审核中，1-申请已同意,2-商家已收货，3-申请已完成，4-申请拒绝）
     * @return BaseResponseMsg
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponseMsg getList(PageQueryModel pageQueryModel,@RequestParam(value = "status", required = false) String status) {

        BrandshopUser brandshopUser = (BrandshopUser) authorizationService.getAuthenticatedUser();

        if (brandshopUser == null) {
            return new BaseResponseMsg(false, TopConstants.BRANDSHOPUSERLOGOUT);
        }

        try {
            return bssOrderRestService.getOrderReturnList(status, brandshopUser.getBrandshop().getId(),pageQueryModel);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.GETINFOFAIL);
        }
    }

    /**
     * 获取退货订单详情
     *
     * @param orderReturnID 退货订单ID
     * @return BaseResponseMsg
     */
    @RequestMapping(value = "/getDetails", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponseMsg getDetails(@RequestParam(value = "orderReturnID") Long orderReturnID) {

        try {
            return bssOrderRestService.getOrderReturnDetails(orderReturnID);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.GETINFOFAIL);
        }
    }

    /**
     * 退货订单审核
     *
     * @param orderReturnID 退货订单ID（1-申请已同意，4-申请拒绝）
     * @return BaseResponseMsg
     */
    @RequestMapping(value = "/putVerify", method = {RequestMethod.GET, RequestMethod.PUT})
    @ResponseBody
    @Transactional
    public BaseResponseMsg putVerify(@RequestParam(value = "orderReturnID") Long orderReturnID,
                                     @RequestParam(value = "status") String status,
                                     @RequestParam(value = "sendAddress", required = false) String sendAddress) {

        try {
            return bssOrderRestService.verifyOrderReturn(orderReturnID, status, sendAddress);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.VERIFYERROR);
        }
    }

    /**
     * 退货订单确认收货（门店收货）
     *
     * @param orderReturnID 退货订单ID
     * @return BaseResponseMsg
     */
    @RequestMapping(value = "/putReceived", method = {RequestMethod.GET, RequestMethod.PUT})
    @ResponseBody
    @Transactional
    public BaseResponseMsg putReceived(@RequestParam(value = "orderReturnID") Long orderReturnID) {

        try {
            return bssOrderRestService.receiveOrderReturn(orderReturnID);
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.VERIFYERROR);
        }
    }


}
