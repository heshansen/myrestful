package com.topbaby.interceptor;

import com.topbaby.entity.AccessKeyEntity;
import com.topbaby.exceptions.AccessDeniedException;
import com.topbaby.exceptions.AccountNotFoundException;
import com.topbaby.exceptions.TopMissingServletRequestParameterException;
import com.topbaby.service.AccessKeyService;
import com.topbaby.service.HmacSignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

/**
 * Created by Qing on 04/01/2017.
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    AccessKeyService accessKeyService;

    @Autowired
    HmacSignatureService signatureService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessKeyId = request.getParameter("accessKeyId");
        String signature = request.getParameter("signature");
        String expires = request.getParameter("expires");

        if(accessKeyId == null){
            throw new TopMissingServletRequestParameterException("accessKeyId", "String");
        }

        AccessKeyEntity accessKeyEntity = accessKeyService.getAccessKey(accessKeyId);

        if (accessKeyEntity == null) {
            throw new AccountNotFoundException("账号不存在");
        }

        //  通过type的检查, 提供了不同type 不同认证方式的可能性, 针对H2M 类型的appliction 暂时不进行认证
        if(accessKeyEntity.getType()=="H2M"){
            return true;
        }

        if(signature == null){
            throw new TopMissingServletRequestParameterException("signature", "String");
        }

        if(expires == null){
            throw new TopMissingServletRequestParameterException("Expires", "String");
        }

        long currentTime = Instant.now().getEpochSecond();

        long client_expires = Long.parseLong(expires);

        if(currentTime > client_expires){
            throw new Exception("请求过期");
        }

        String stringToSign =  request.getMethod() + "\n" +
                request.getRequestURI() + "\n" +
                client_expires + "\n";

        boolean authResult = signatureService.HmacSha1Auth(accessKeyEntity.getAccess_secret(), stringToSign, signature);

        if(authResult == false){
            throw new AccessDeniedException("认证失败");
        }

        return true;
    }
}
