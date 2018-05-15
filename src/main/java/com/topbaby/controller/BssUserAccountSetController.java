package com.topbaby.controller;

import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.verify.entity.Verify;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssUserAccountSetService;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 导购员账户维护
 * Created by heshansen on 16-12-16.
 */
@Controller
@RequestMapping(value = "/restful/bss/account")
public class BssUserAccountSetController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private BssUserAccountSetService bssUserAccountSetService;

    /**
     * 导购员基本信息查询（包含银行卡等信息）
     *
     * @return
     */
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    @ResponseBody
    public Object getDetail() {
//        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Long userId = Long.valueOf(821);//用户id:brandshopUser.getId()
        log.info("导购员基本信息维护：userId=" + userId);
        BrandshopUser bsUser;
        try {
            bsUser = bssUserAccountSetService.getDetail(userId);
            if (bsUser == null) {
                return new BaseResponseMsg(false, "导购员信息为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false, "导购员信息查询失败！");
        }
        return bsUser;
    }

    /**
     * <p>
     * 　保存并提交审核
     * </p>
     *
     * @param bsUser         　导购员门店信息
     * @param idCardFrontImg 身份证正面图片id
     * @param idCardBackImg  身份证反面图片id
     * @return
     * @since 1.0.0
     */
    @RequestMapping(value = "/postUpdate", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseMsg postUpdate(
            BrandshopUser bsUser,
            @RequestParam(value = "idCardFrontImg", required = false) String idCardFrontImg,
            @RequestParam(value = "idCardBackImg", required = false) String idCardBackImg) {
        //默认值：测试用
        if (bsUser.getLoginId() == null) {
            bsUser.setId(Long.valueOf(821));
            bsUser.setBrandshopSeq(Long.valueOf(254));
            bsUser.setCardNumber("1111111111111111111");
        }
        log.info("手机前端提交审核:用户id=" + bsUser.getId() + ",idCardFrontImg=" + idCardFrontImg + ",idCardBackImg=" + idCardBackImg);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("bsUser",bsUser);
        param.put("idCardFrontImg",idCardFrontImg);
        param.put("idCardBackImg",idCardBackImg);
        BaseResponseMsg result;
        try {
            result = bssUserAccountSetService.postUpdate(param);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"导购员账号信息更新异常！");
        }
        return result;
    }

    /**
     * <p>
     * 审核记录列表数据
     * </p>
     * 　1.支持多状态查询
     * 2.支持分页查询
     *
     * @param status         审核状态（可选）
     * @param pageQueryModel 　分页参数
     * @return
     */
    @RequestMapping(value = "/verifyList", method = RequestMethod.GET)
    @ResponseBody
    public Object verifyList(@RequestParam(value = "status", required = false) String status,
                             PageQueryModel pageQueryModel) {
        Map<String, Object> param = new HashMap<String, Object>();
//        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Long userId = Long.valueOf(821);//用户id:brandshopUser.getId()
        log.info("审核列表：userId=" + userId);
        param.put("status", status);
        param.put("userId", userId);
        param.put("pageQueryModel", pageQueryModel);
        PageModel<Verify> pageModel;
        try {
            pageModel = bssUserAccountSetService.verifyList(param);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false, "获取审核信息失败！");
        }

        return pageModel;
    }
}
