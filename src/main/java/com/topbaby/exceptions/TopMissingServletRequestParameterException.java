package com.topbaby.exceptions;

import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * Created by Qing on 06/01/2017.
 */
public class TopMissingServletRequestParameterException extends MissingServletRequestParameterException {

    public TopMissingServletRequestParameterException(String parameterName, String parameterType){
        super(parameterName, parameterType);
    }

    @Override
    public String getMessage() {
        return "Http 请求缺少参数: " + this.getParameterName();
    }
}
