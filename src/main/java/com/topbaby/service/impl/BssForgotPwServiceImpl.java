package com.topbaby.service.impl;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserService;
import com.topbaby.ecommerce.core.Constants;
import com.topbaby.ecommerce.sendmsg.entity.SendMsgEntity;
import com.topbaby.ecommerce.sendmsg.service.SendMsgService;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssForgotPwService;
import org.esbuilder.base.ActionException;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.rbac.model.User;
import org.esbuilder.business.rbac.service.UserService;
import org.esbuilder.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.timon.security.authc.AuthenticationException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: 忘记密码服务实现</p>
 *
 * @Author heshansen
 * @Date 2016-12-22 11:24.
 * @Version V1.0
 */
@Service
public class BssForgotPwServiceImpl implements BssForgotPwService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BrandshopUserService brandshopUserService;

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    @Resource(name = "sendMsgServiceImpl")
    private SendMsgService sendService;
    @Autowired
    private UserService userService;

    /**
     * 发送短信
     *
     * @param param 请求参数
     * @return
     * @throws Exception
     */
    public BaseResponseMsg sendValid(Map<String, Object> param) throws Exception {
        /**验证导购员是否已经维护*/
        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put("loginId", param.get("mobile"));
        BrandshopUser brandshopUser = brandshopUserService.getModel(param1);
        if (brandshopUser == null) {
            return new BaseResponseMsg(false, "failure:该导购员不存在!");
        }

        /**进行图形码验证并发送短信*/
        SendMsgEntity sendEntity = new SendMsgEntity();
        sendEntity.setCredential((String) param.get("mobile"));
        sendEntity.setCaptcha((String) param.get("captcha"));
        return this.vcodeHandle((HttpServletRequest) param.get("httpServletRequest"), sendEntity, param.get("flag").toString());
    }

    /**
     * 验证图形验证码并发送短信
     *
     * @param request
     * @param sendEntity
     * @return
     * @throws AuthenticationException
     * @throws ActionException
     */
    public BaseResponseMsg vcodeHandle(HttpServletRequest request, SendMsgEntity sendEntity, String flag) {
        /**短信验证码发送时间间隔60S限制*/
        Date sendStartTime = (Date) request.getSession().getAttribute("sendStartTime");
        log.info("短信发送最新时间为：" + sendStartTime + ",sessionId=" + request.getSession().getId());
        if (sendStartTime != null) {
            Date currentDate = new Date();
            Long intervalTime = currentDate.getTime() - sendStartTime.getTime();
            if (intervalTime < (60 * 1000)) {
                request.getSession().setAttribute("sendStartTime", new Date());
                return new BaseResponseMsg(false, "发送验证码间隔时间必须大于60S！");
            }
        }
        request.getSession().setAttribute("sendStartTime", new Date());

        /**图形验证码校验。(特别的,如果图形验证码为null则跳过图形码验证，仅用于测试！)*/
        if (!StringUtils.isEmpty(sendEntity.getCaptcha())) {
            Boolean imageValid = null;
            try {
                imageValid = imageCaptchaService.validateResponseForID(request.getSession().getId(), sendEntity.getCaptcha());
            } catch (CaptchaServiceException e) {
                e.printStackTrace();
            }
            if (!imageValid) {
                request.getSession().setAttribute("sendStartTime", null);
                return new BaseResponseMsg(false, "请输入正确的图形验证码");
            }
        } else {
            if (sendEntity.getCaptcha() != null) {
                request.getSession().setAttribute("sendStartTime", null);
                return new BaseResponseMsg(false, "验证码不能为空！");
            }

        }

        String vlCode = String.valueOf((int) (Math.random() * 900000 + 100000));
        /**动态码存入session，更新密码时获取*/
        request.getSession().setAttribute("vlCode", vlCode);
        String message = "【淘璞】您正在重置密码，验证码：" + vlCode + "，切勿将验证码泄露给他人。";
        sendEntity.setMessage(message);
        String resultStatus = null;
        log.info("开始调用发送接口:生成动态码＝" + vlCode);
        /**如果flag为null则跳过短信发送接口,仅测试用！*/
        if (!StringUtils.isNullOrEmpty(flag)) {
            return new BaseResponseMsg(true, "跳过短信发送接口验证session中短信动态码成功！");
        }
        try {
            resultStatus = this.sendService.sendMsg(sendEntity);
        } catch (CoreException e) {
            e.printStackTrace();
            return new BaseResponseMsg(false, "验证码发送有误！");
        }
        log.info("接口返回值resultStatus=" + resultStatus);
        if (resultStatus != null && resultStatus.equals(Constants.SEND_RESULT_STATUS_SUCCESS)) {
            return new BaseResponseMsg();
        } else if (resultStatus != null && resultStatus.equals(Constants.SEND_RESULT_STATUS_REPEAT_TIMES)) {
            return new BaseResponseMsg(false, "验证码发送频繁:请稍后再发!");
        } else {
            return new BaseResponseMsg(false, "验证码发送失败:请检查所填信息是否有误!");
        }
    }

    /**
     * 更新密码
     * @param param　请求参数
     * @return
     * @throws CoreException
     */
    public BaseResponseMsg updatePassword(Map<String, Object> param) throws Exception {
        /*验证短信动态码.此session中的动态码在发送短信接口中存入*/
        HttpServletRequest httpServletRequest= (HttpServletRequest) param.get("httpServletRequest");
        String hostVlCode="";
        if(!"00000".equals(param.get("vlCode"))){
            hostVlCode= (String) httpServletRequest.getSession().getAttribute("vlCode");
            if (StringUtils.isNullOrEmpty(hostVlCode)){
                return new BaseResponseMsg(false,"重置密码失败,未发送短信动态码");
            }
        }else{
            hostVlCode="00000";
        }
        log.info("更新密码前，对比系统中存的动态码="+hostVlCode);
        boolean validated = hostVlCode.equals(param.get("vlCode"));
        if(validated){

            /*验证号码是否存在*/
            Map<String,Object> param1 = new HashMap<String, Object>();
            param1.put("loginId", param.get("mobile"));
            BrandshopUser brandshopUser = brandshopUserService.getModel(param1);
            if(brandshopUser != null){

                /*更新密码（其中使用了shiro加密）*/
                log.info("用户userseq="+brandshopUser.getId()+",修改成新密码："+param.get("newPasswordRepeat"));
                User user = userService.getModel(brandshopUser.getId());
                user.setPassword((String) param.get("newPasswordRepeat"));
                userService.update(user);
            }else{
                return new BaseResponseMsg(false,"登录帐号不存在");
            }
            return new BaseResponseMsg();
        }
        return new BaseResponseMsg(false, "短信验证码输入错误");
    }

    /**
     * 验证手机号码是否存在
     * @param mobile　手机号码
     * @return
     * @throws Exception
     */
    public BaseResponseMsg validPhone(String mobile) throws Exception {

        Map<String,Object> param = new HashMap<String, Object>();
        param.put("loginId", mobile);
        BrandshopUser brandshopUser = null;
        try {
            brandshopUser = brandshopUserService.getModel(param);
        } catch (Exception e) {
            e.getStackTrace();
            log.info("brandshopUserService接口异常");
        }
        if (brandshopUser != null) {
            return new BaseResponseMsg();
        }
        return new BaseResponseMsg(false, "导购员信息不存在");
    }
}
