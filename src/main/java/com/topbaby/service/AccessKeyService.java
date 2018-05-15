package com.topbaby.service;

import com.topbaby.entity.AccessKeyEntity;

/**
 * <p>认证服务接口</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 17-1-5
 */
public interface AccessKeyService {

    /**
     * 获取认证信息实体
     * @param accessKeyId access_key_id
     */
    AccessKeyEntity getAccessKey(String accessKeyId);


}
