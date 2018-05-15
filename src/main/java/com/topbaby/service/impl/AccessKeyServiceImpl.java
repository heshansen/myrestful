package com.topbaby.service.impl;

import com.topbaby.dao.AccessKeyDao;
import com.topbaby.entity.AccessKeyEntity;
import com.topbaby.service.AccessKeyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>认证服务接口实现</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 17-1-5
 */
@Service
public class AccessKeyServiceImpl implements AccessKeyService{

    @Resource(name = "AccessKeyDao")
    AccessKeyDao accessKeyDao;

    public AccessKeyEntity getAccessKey(String accessKeyId) {
        return accessKeyDao.getSecretByKeyId(accessKeyId);
    }
}
