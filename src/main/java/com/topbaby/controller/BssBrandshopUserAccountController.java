package com.topbaby.controller;

import com.topbaby.common.TopConstants;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.entity.UserAccountHistoryPageParam;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BrandshopUserAccountRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.timon.security.authz.IAuthorizationService;

import java.math.BigDecimal;

/**
 * <p>门店导购员账户信息</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-20
 */
@Controller
@RequestMapping("/restful/bss/user")
public class BssBrandshopUserAccountController {

    @Autowired
    private IAuthorizationService authorizationService;

    @Autowired
    private BrandshopUserAccountRestService accountRestService;


    /**
     * 获取导购员账户信息接口(账户总额，账户余额，提现总额)
     */
    @RequestMapping(value = "/getAccount", method = {RequestMethod.GET})
    @ResponseBody
    public Object getAccount() {

        BrandshopUser brandshopUser = authorizationService.getAuthenticatedUser(BrandshopUser.class);

        try {
            return accountRestService.getUserAccount(brandshopUser.getId());
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.GETINFOFAIL);
        }
    }

    /**
     * 导购员提现
     *
     * @param amount 提现金额
     */
    @RequestMapping(value = "/postAmount", method = {RequestMethod.POST})
    @ResponseBody
    @Transactional
    public BaseResponseMsg postAmount(@RequestParam(value = "amount", required = false) BigDecimal amount) {
        BrandshopUser brandshopUser = authorizationService.getAuthenticatedUser(BrandshopUser.class);

        try {
            return accountRestService.withdrawCash(amount, brandshopUser.getId());
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.USERWITHDRAWERROR);
        }
    }

    /**
     * 获取导购员账户历史记录（资金明细）
     *
     * @param pageParam 查询参数对象
     * @see com.topbaby.entity.UserAccountHistoryPageParam
     */
    @RequestMapping(value = "/getAccountHistory", method = {RequestMethod.GET})
    @ResponseBody
    public Object getAccountHistory(UserAccountHistoryPageParam pageParam) {

        BrandshopUser brandshopUser = authorizationService.getAuthenticatedUser(BrandshopUser.class);
        try {
            return accountRestService.getAccountHistory(pageParam, brandshopUser.getId());
        } catch (Exception e) {
            return new BaseResponseMsg(false, TopConstants.GETINFOFAIL);
        }
    }
}
