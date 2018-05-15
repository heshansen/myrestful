package com.topbaby.service.impl;

import com.topbaby.service.HmacSignatureService;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by Qing on 06/01/2017.
 */
@Service
public class HmacSha1AuthServiceImpl implements HmacSignatureService {
    public boolean HmacSha1Auth(String key, String data, String signature) throws UnsupportedEncodingException {

        String hamc = HmacUtils.hmacSha1Hex(key.getBytes("UTF-8"), data.getBytes("UTF-8"));

        String base64_Hamc = Base64.getEncoder().encodeToString(hamc.getBytes());
        return (base64_Hamc.equals(signature));
    }
}
