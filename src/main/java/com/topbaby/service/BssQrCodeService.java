package com.topbaby.service;

import com.topbaby.entity.BssQrCodeInfo;
import com.topbaby.entity.base.BaseResponseMsg;

/**
 * <p>Title: </p>
 *
 * @Author heshansen
 * @Date 2016-12-22 19:28.
 * @Version V1.0
 */
public interface BssQrCodeService {
    /**
     * 获得门店名称、图片url及二维码url
     */
    BssQrCodeInfo getBsPicture(Long userId) throws Exception;

    /**
     * 获得二维码url
     */
    BaseResponseMsg getQrCode(Long userId) throws Exception;
}
