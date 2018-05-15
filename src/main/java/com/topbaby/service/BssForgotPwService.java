package com.topbaby.service;

import com.topbaby.entity.base.BaseResponseMsg;

import java.util.Map;

/**
 * <p>Title: 忘记密码服务</p>
 *
 * @version v1.0
 * @auther heshansen
 * @date 16-12-22 上午10:58
 */
public interface BssForgotPwService {
    /**
     * 发送短信
     */
    BaseResponseMsg sendValid(Map<String,Object> param) throws Exception;
    /**
     * 更新密码
     */
    BaseResponseMsg updatePassword(Map<String,Object> param) throws Exception;

    /**
     * 验证手机号码是否存在
     */
    BaseResponseMsg validPhone(String mobile) throws Exception;
}
