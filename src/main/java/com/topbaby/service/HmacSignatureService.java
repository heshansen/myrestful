package com.topbaby.service;

import java.io.UnsupportedEncodingException;

/**
 * Created by Qing on 06/01/2017.
 */
public interface HmacSignatureService {
    /**
     * HmacSha1 authentication
     */
    boolean HmacSha1Auth(String key, String data, String signature) throws UnsupportedEncodingException;
}
