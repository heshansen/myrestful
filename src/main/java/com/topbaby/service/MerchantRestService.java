package com.topbaby.service;

import com.topbaby.ecommerce.merchant.entity.Merchant;

import java.util.Map;

/**
 * Created by heshansen on 16-12-2.
 */
public interface MerchantRestService {
    Boolean existMerchant(Long merchantSeq) throws Exception;
    Merchant getMerchant(Long merchantSeq) throws Exception;
    Boolean addMerchant(Map<String,Object> fileMap,Merchant merchant) throws Exception;
}
