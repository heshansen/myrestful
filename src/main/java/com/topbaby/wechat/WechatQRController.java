package com.topbaby.wechat;

import com.topbaby.entity.base.BaseResponse;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.QRCodeServiceImpl;
import com.topbaby.wechat.token.TokenGenerator;
import org.esbuilder.spring.orm.mybatis.session.ConfigurableSqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;

/**
 * <p>微信带参二维码</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 17-1-4
 */
@Controller
@Transactional(value = "restTXManager")
@RequestMapping("/restful/wechat/qrCode")
public class WechatQRController {

    @Autowired
    QRCodeServiceImpl qrCodeService;

    @Value("${wechat.appid}")
    String appId;

    @Value("${wechat.appsecret}")
    String appSecret;

    @RequestMapping(value = "/getQRCodeUrl", method = RequestMethod.GET)
    @ResponseBody
    public Object getQRCodeUrl(@RequestParam(name = "userId")Long userId,@RequestParam(name = "type")String type) {
        try {


            TokenGenerator generator = new TokenGenerator(appId, appSecret);

            String token = generator.accessTokenEntity().getAccess_token();

            BaseResponse baseResponse = qrCodeService.getQRCodeUrl(userId, type, token);

            return baseResponse;
        } catch (IOException e) {
            return new BaseResponseMsg(false, " IOException error");
        } catch (ParseException e) {
            return new BaseResponseMsg(false, "ParseException error");
        }
    }
}
