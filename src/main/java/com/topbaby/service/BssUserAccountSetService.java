package com.topbaby.service;

import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.verify.entity.Verify;
import com.topbaby.entity.base.BaseResponseMsg;
import org.esbuilder.business.model.PageModel;

import java.util.Map;

/**
 * <p>Title: 导购员账户维护</p>
 *
 * @Author heshansen
 * @Date 2016-12-23 09:53.
 * @Version V1.0
 */
public interface BssUserAccountSetService {
    /**
     * 获得导购员账户基本信息
     */
    BrandshopUser getDetail(Long userId) throws Exception;

    /**
     * 更新导购员账户信息
     */
    BaseResponseMsg postUpdate(Map<String, Object> param) throws Exception;

    /**
     * 导购员账户审核列表
     */
    PageModel<Verify> verifyList(Map<String, Object> param) throws Exception;

}
