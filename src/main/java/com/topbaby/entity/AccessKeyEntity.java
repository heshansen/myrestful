package com.topbaby.entity;

import com.topbaby.entity.base.BaseEntity;

/**
 * <p>认证实体</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 17-1-5
 */
public class AccessKeyEntity extends BaseEntity{

    /** 第三方用户唯一凭证 */
    private String access_key_id;
    /** 第三方用户唯一凭证密钥 */
    private String access_secret;
    /** 认证类型 */
    private String type;
    /** 创建时间 */
    private String create_time;
    /** 更新时间 */
    private String update_time;
    /** 备用状态 */
    private String status;

    public String getAccess_key_id() {
        return access_key_id;
    }

    public void setAccess_key_id(String access_key_id) {
        this.access_key_id = access_key_id;
    }

    public String getAccess_secret() {
        return access_secret;
    }

    public void setAccess_secret(String access_secret) {
        this.access_secret = access_secret;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
