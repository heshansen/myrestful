package com.topbaby.controller;

import com.topbaby.ecommerce.gift.vo.GiftVO;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssGiftService;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.timon.security.authc.IAuthenticationService;

import java.util.HashMap;
import java.util.Map;

/**
 * 门店赠品管理
 * Created by heshansen on 16-12-16.
 */
@RestController
@RequestMapping(value = "/restful/bss/gift")
public class BssGiftController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IAuthenticationService authenticationService;
    @Autowired
    private BssGiftService bssGiftService;

    /**
     * 赠品管理list；列表
     * 　1.支持分页查询
     *
     * @param createDate         领取时间
     * @param endDate            　兑换时间
     * @param status               会员赠品使用状态    0=未使用;1＝已使用
     * @param pageQueryModel     　分页参数
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Object getList(@RequestParam(value = "createDate", required = false) String createDate,
                       @RequestParam(value = "endDate", required = false) String endDate,
                       @RequestParam(value = "status", required = false) String status,
                       PageQueryModel pageQueryModel) {

        //当前登录用户（导购员）
//        IUser user = authenticationService.getAuthenticatedUser();
        //测未使用：createDate＝'2016-08-11 21:06:45';status=0;brandshopSeq=113;userId=677
        //测已使用；endDate＝'2016-09-14 18:55:41';status=1;brandshopSeq=213;userId=1127
        Long userId;//用户id:brandshopUser.getId()
        if ("0".equals(status)) {
            userId = Long.valueOf(677);
        }else {
            userId = Long.valueOf(1127);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId",userId);
        param.put("createDate", createDate);
        param.put("endDate", endDate);
        param.put("status", status);
        param.put("pageQueryModel", pageQueryModel);

        PageModel<GiftVO> giftPageList;
        try {
            giftPageList=bssGiftService.getGiftPageList(param);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false, "赠品信息查询异常！");
        }

        return giftPageList;
    }

}
