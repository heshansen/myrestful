package com.topbaby.dao;

import com.topbaby.entity.AccessKeyEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>认证信息dao</p>
 * details：
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 17-1-5
 */
public interface AccessKeyDao {

    @Select("SELECT * FROM access_key WHERE access_key_id = #{accessKeyId}")
    AccessKeyEntity getSecretByKeyId(@Param("accessKeyId") String accessKeyId);
}
