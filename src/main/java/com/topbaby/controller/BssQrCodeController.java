package com.topbaby.controller;

import com.topbaby.entity.BssQrCodeInfo;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssQrCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.timon.security.authc.IAuthenticationService;

import javax.servlet.http.HttpServletResponse;

/**
 * 门店二维码
 * Created by heshansen on 16-12-14.
 */
@RestController
@RequestMapping(value = "/restful/bss/qrCode")
public class BssQrCodeController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected IAuthenticationService authenticationService;
    @Autowired
    private BssQrCodeService bssQrCodeService;

    /**
     * 门店图片二维码获取
     *
     * @return BssQrCodeInfo 门店图片二维码实体类
     */
    @RequestMapping(value = "/getBsPicture", method = RequestMethod.GET)
    public Object getBsPicture(HttpServletResponse response) {
        //跨域响应配置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        //通过shrio获得登录用户ID
//        IUser user = this.authenticationService.getAuthenticatedUser();
        Long userId = Long.valueOf(806);//默认值:user.getId()
        log.info("begin----userId="+userId);
        BssQrCodeInfo qrCode = new BssQrCodeInfo();
        try {
            qrCode = bssQrCodeService.getBsPicture(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrCode;
    }


    /**
     * 活动门店二维码appUrl
     *
     * @return
     */
    @RequestMapping(value = "/getQrCode", method = RequestMethod.GET)
    public BaseResponseMsg getQrCode(HttpServletResponse response) {
        //跨域响应配置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        //通过shrio获得登录用户ID
//        IUser user = this.authenticationService.getAuthenticatedUser();
        Long userId = Long.valueOf(806);//默认值:user.getId
        log.info("begin----userId="+userId);
        BaseResponseMsg result;
        try {
            result = bssQrCodeService.getQrCode(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false, "导购员信息查询失败！");
        }
        return result;
    }
}
