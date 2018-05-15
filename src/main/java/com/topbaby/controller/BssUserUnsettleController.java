package com.topbaby.controller;

import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUserOrderActivityRelation;
import com.topbaby.ecommerce.core.entity.Member;
import com.topbaby.entity.BrandshopUserUnsettlesInfo;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BrandshopUnsettleRestService;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.timon.security.authc.IAuthenticationService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xianghui
 * 导购员未结算明细list
 */
@Controller
@RequestMapping(value="/restful/bss/userUnsettle")
public class BssUserUnsettleController {

    @Autowired
    IAuthenticationService authenticationService ;
    @Autowired
    private BrandshopUnsettleRestService brandshopUnsettleRestService;

    @RequestMapping(value="/getUnsettles",method=RequestMethod.GET)
    @ResponseBody
    public BaseResponseMsg getUnsettle(PageQueryModel pageQueryModel){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        BrandshopUserUnsettlesInfo brandshopUserUnsettlesInfo = null;
        try {
            brandshopUserUnsettlesInfo = brandshopUnsettleRestService.getUnliquidatedList(brandshopUser.getId(),pageQueryModel);
        } catch (CoreException e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"获取未结算明细出错");
        }
        return brandshopUserUnsettlesInfo ;
    }

    /**
     * 导购员会员发展未结算list
     * @param pageQueryModel
     * @return
     */
    @RequestMapping(value="/getMemberDevs",method=RequestMethod.GET)
    @ResponseBody
    public BaseResponseMsg memberDevUnliquidateds(PageQueryModel pageQueryModel){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Map params = new HashMap();
        BrandshopUserUnsettlesInfo brandshopUserUnsettlesInfo = null ;
        params.put("userSeq", brandshopUser.getId());
        /** o代表未达标 */
        params.put("reachStandard", "0");
        try {
            brandshopUserUnsettlesInfo = brandshopUnsettleRestService.
                    memberdevUnliquidatedList(brandshopUser.getId(),params,pageQueryModel);
        } catch (CoreException e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"获取会员发展未结算出错");
        }
        return brandshopUserUnsettlesInfo ;

    }

    /**
     * 导购员奖励活动发展会员未结算list
     * @param pageQueryModel
     * @return
     */
    @RequestMapping(value="/getRewActMemDevs",method=RequestMethod.GET)
    @ResponseBody
    public Object rewActMemDevUnliquidateds(PageQueryModel pageQueryModel){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();

        PageModel<Member> result = null;
        try {
            result = brandshopUnsettleRestService.
                    rewActMemDevUnliquidated(brandshopUser,pageQueryModel);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"获取奖励活动会员发展未结算出错");
        }
        return result ;
    }

    /**
     * 导购员奖励活动订单未结算list
     * @param pageQueryModel
     * @return
     */
    @RequestMapping(value="/getRewActOrders",method=RequestMethod.GET)
    @ResponseBody
    public Object rewActOrderUnliqudateds(PageQueryModel pageQueryModel){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();

        PageModel<BrandshopUserOrderActivityRelation> result = null;
        try {
            result = brandshopUnsettleRestService.
                    rewActOrderdealUnliqudated(brandshopUser.getId(),pageQueryModel);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"获取奖励活动订单未结算出错");
        }
        return result;
    }

}
