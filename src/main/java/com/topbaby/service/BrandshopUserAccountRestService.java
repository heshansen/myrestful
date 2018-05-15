package com.topbaby.service;

import com.topbaby.entity.UserAccountHistoryPageParam;
import com.topbaby.entity.base.BaseResponseMsg;

import java.math.BigDecimal;

/**
 * <p>导购员账户服务类</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-20
 */
public interface BrandshopUserAccountRestService {

    /**
     * 获取导购员账户信息
     */
    Object getUserAccount(Long brandshopUserID) throws Exception;

    /**
     * 导购员提现申请
     */
    BaseResponseMsg withdrawCash(BigDecimal amount, Long brandshopUserID) throws Exception;

    /**
     * 导购员提现明细
     */
    Object getAccountHistory(UserAccountHistoryPageParam accountParam, Long brandshopUserID) throws Exception;

}
