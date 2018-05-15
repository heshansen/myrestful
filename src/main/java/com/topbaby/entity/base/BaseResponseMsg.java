package com.topbaby.entity.base;

import com.topbaby.common.TopConstants;

/**
 * details： 返回信息(返回的json对象)的基础类
 * Created by sxy on 16-12-1.
 */
public class BaseResponseMsg {
    public Boolean result;
    public String message;

    public BaseResponseMsg() {
        this.result = true;
        this.message = TopConstants.SUCCESSINFO;
    }

    public BaseResponseMsg(Boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
