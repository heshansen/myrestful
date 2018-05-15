package com.topbaby.controller;

import com.topbaby.ecommerce.brandshop.entity.*;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserSettlementService;
import com.topbaby.ecommerce.core.Constants;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BrandshopSettleRestService;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.timon.security.authc.IAuthenticationService;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xianghui
 * 导购员提现,导购员结算
 */
@Controller
@RequestMapping(value="/restful/bss/userSettle")
public class BssUserSettleController {

    @Autowired
    private BrandshopSettleRestService brandshopSettleRestService;
    @Autowired
    private BrandshopUserSettlementService brandshopUserSettlementService ;
    @Autowired
    IAuthenticationService authenticationService ;
    /**
     * 导购员提现明细list
     * @return PageModel<BrandshopUserWithdraw>
     */
    @RequestMapping(value="/getCashes",method= RequestMethod.GET)
    @ResponseBody
    public Object getCashes(PageQueryModel pageQueryModel,
                            @RequestParam(value="startTime",required=false) String startTime){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("applyUser", brandshopUser.getId());
        params.put("applyDateTime", startTime);
        params.put(Constants.ORDERRULES, Constants.ORDERRULES);

        PageModel<BrandshopUserWithdraw> pageModel = null;
        try {
            pageModel = brandshopSettleRestService.getCashes(params,pageQueryModel);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"提现出错");
        }

        return pageModel ;
    }

    /**
     * 导购员结算明细list
     * @return  PageModel<BrandshopUserSettlement>
     */
    @RequestMapping(value="/getSettles",method= RequestMethod.GET)
    @ResponseBody
    public Object getSettles(PageQueryModel pageQueryModel,@RequestParam(value="startTime",required=false) String startTime,
                             @RequestParam(value="endTime",required=false) String endTime){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userSeq", brandshopUser.getId());
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        PageModel<BrandshopUserSettlement> pageModel = null;
        try {
            pageModel = brandshopSettleRestService.getBalances(params,pageQueryModel);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"获取结算出错");
        }

        return pageModel ;
    }

    /**
     * 导购员订单结算list,根据结算id查询结算时间.
     * @param id 为结算id
     * @return PageModel<BrandshopUserOrderProfit>
     */
    @RequestMapping(value="/getSettleOrders",method= RequestMethod.GET)
    @ResponseBody
    public Object getSettleOrders(PageQueryModel pageQueryModel,@RequestParam(value="id",required = false) Long id){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Map<String, Object> params = new HashMap<String, Object>();
        PageModel<BrandshopUserOrderProfit> pageModel = null;
        if(id==null){
            return new BaseResponseMsg(false,"查询导购员订单结算出错");
        }
        try {
            params.put("settlementDate", brandshopUserSettlementService.getModel(id)
                    .getSettlementDate());
            params.put("userSeq", brandshopUser.getId());

            pageModel = brandshopSettleRestService.getBalanceOrders(params,pageQueryModel);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"获取订单结算出错");
        }

        return pageModel ;
    }

    /**
     * 导购员会员发展结算list
     * @param id 为结算id
     * @return PageModel<BrandshopUsersMemberDevProfit>
     */
    @RequestMapping(value="/getMemDevSettles",method= RequestMethod.GET)
    @ResponseBody
    public Object getMemDevSettles(@RequestParam(value="id",required=false) Long id,PageQueryModel pageQueryModel){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userSeq",brandshopUser.getId());
        if(id==null){
            return new BaseResponseMsg(false,"获取发展会员结算出错");
        }
        PageModel<BrandshopUsersMemberDevProfit> pageModel = null;
        try {
            pageModel = brandshopSettleRestService.memberDevBalances(id,params,pageQueryModel);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"获取会员发展结算出错");
        }

        return pageModel ;
    }

    /**
     * 导购员奖励活动结算
     * @param id 为结算id
     * @return PageModel<BrandshopUserRewardActivityProfit>
     */
    @RequestMapping(value="/getRewActSettles",method= RequestMethod.GET)
    @ResponseBody
    public Object getRewActSettles(@RequestParam(value="id",required=false) Long id ,PageQueryModel pageQueryModel){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Map<String, Object> params = new HashMap<String, Object>();
        if(id==null){
            return new BaseResponseMsg(false,"获取奖励活动结算出错");
        }
        params.put("userSeq",brandshopUser.getId());

        PageModel<BrandshopUserRewardActivityProfit> pageModel = null;
        try {
            pageModel = brandshopSettleRestService.rewardActivityBalances(id,params,pageQueryModel);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"获取奖励活动结算出错");
        }

        return pageModel ;
    }
}




