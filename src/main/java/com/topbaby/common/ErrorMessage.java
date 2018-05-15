package com.topbaby.common;

/**
 * Created by Qing on 05/01/2017.
 *
 * POJO object for all restful exceptions
 */
public class ErrorMessage {
    private int code;

    private String message;

    public ErrorMessage(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
