package com.topbaby.controller;

import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssForgotPwService;
import org.esbuilder.base.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 忘记密码
 * Created by heshansen on 16-12-8.
 */
@Controller
@RequestMapping(value = "/restful/bss/forgotPw")
public class BssForgotPwController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BssForgotPwService bssForgotPwService;

    /**
     * 发送短信验证码
     *
     * 1,检查用户是否存在
     * 2,短信发送间隔限制
     * 3,验证图形验证码
     * 4,发送短信
     *
     * @param httpServletRequest 请求
     * @param mobile 　手机号码
     * @param captcha    　图形验证码(null=跳过图形验证码)
     * @param flag 标记（!null＝跳过短信发送接口）
     * @return
     */
    @RequestMapping(value = "/sendValid", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseMsg sendValid(HttpServletRequest httpServletRequest,HttpServletResponse response,
                                     @RequestParam(value = "mobile", required = true) String mobile,
                                     @RequestParam(value = "captcha", required = false) String captcha,
                                     @RequestParam(value = "flag", required = false)String flag) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("httpServletRequest",httpServletRequest);
        param.put("mobile", mobile);
        param.put("captcha",captcha);
        param.put("flag",flag);
        log.info("传入的号码为：" + mobile+",验证码＝"+captcha+",sessionId="+httpServletRequest.getSession().getId());
        BaseResponseMsg result;
        try {
            result = bssForgotPwService.sendValid(param);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"短信发送异常！");
        }
        return result;
    }



    /**
     * 重置密码
     *
     * １,先验证短信动态码
     * 2,再确保用户已维护
     * 3,最后更新密码
     *
     * @param httpServletRequest　请求
     * @param mobile　手机号
     * @param vlCode　短信验证码
     * @param newPassword　新密码
     * @param newPasswordRepeat　确认新密码
     * @return
     * @throws CoreException
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseMsg updatePassword(HttpServletRequest httpServletRequest,
                                          @RequestParam(value = "mobile", required = false) String mobile,
                                          @RequestParam(value = "vlCode", required = false) String vlCode,
                                          @RequestParam(value = "newPassword", required = false) String newPassword,
                                          @RequestParam(value = "newPasswordRepeat", required = false) String newPasswordRepeat){
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("httpServletRequest",httpServletRequest);
        param.put("mobile", mobile);
        param.put("vlCode",vlCode);
        param.put("newPasswordRepeat",newPasswordRepeat);
        log.info("传入的号码为：" + mobile+",短信动态码＝"+vlCode);
        BaseResponseMsg result;
        try {
            result = bssForgotPwService.updatePassword(param);
        } catch (CoreException c) {
            c.printStackTrace();
            return new BaseResponseMsg(false,"系统更新密码异常！");
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponseMsg(false,"更新密码异常");
        }
        return result;
    }

/*
* 下面功能目前仅供测试用！
* */

    /**
     * 使用页面测试图形验证码用
     * @param model
     * @return
     */
    @RequestMapping(value = { "/", "/forgotPassword" }, method = RequestMethod.GET)
    public String getHomePage(ModelMap model) {
        return "forgotPassword";
    }

    /**
     * 验证手机号码(富余接口：仅单独号码验证用)
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/validPhone", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponseMsg validPhone(@RequestParam(value = "mobile", defaultValue = "null") String mobile) {
        log.info("***validPhone begain...mobile=" + mobile);
        BaseResponseMsg result;
        try {
            result = bssForgotPwService.validPhone(mobile);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"号码验证异常！");
        }
        return result;

    }

    /**
     * 图形验证码验证（富余接口：仅单独图形码验证用）
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/validCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseMsg validCaptcha(HttpServletRequest request,
                                        @RequestParam(value = "captcha", required = true) String captcha) {
        log.info("***validCaptcha begain...sessionId=" + request.getSession().getId()+",captcha="+captcha);
        //图形验证码校验
//        if (!StringUtils.isEmpty(captcha)) {
//            if (imageCaptchaService.validateResponseForID(request.getSession().getId(), captcha)) {
//                return new BaseResponseMsg();
//            } else {
//                return new BaseResponseMsg(false, "请输入正确的图形验证码");
//            }
//        } else {
//            return new BaseResponseMsg(false, "验证码不能为空！");
//        }
        return new BaseResponseMsg();
    }


}
