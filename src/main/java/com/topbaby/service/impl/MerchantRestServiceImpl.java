package com.topbaby.service.impl;

import com.topbaby.ecommerce.merchant.entity.Merchant;
import com.topbaby.ecommerce.merchant.service.MerchantService;
import com.topbaby.service.MerchantRestService;
import org.esbuilder.base.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by heshansen on 16-12-2.
 */
@Service
public class MerchantRestServiceImpl implements MerchantRestService{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MerchantService merchantService;

    /**
     * 判断商户是否存在
     * @param merchantSeq
     * @return
     * @throws Exception
     */
    public Boolean existMerchant(Long merchantSeq){
        Merchant merchant=merchantService.getModel(merchantSeq);
        if (merchant!=null){
            return true;
        }
        return false;
    }

    /**
     * 查看商户
     * @param merchantSeq
     * @return
     * @throws Exception
     */
    public Merchant getMerchant(Long merchantSeq){
        log.info("***getMerchant 开始。。。");
        Merchant merchant=merchantService.getModel(merchantSeq);
        return merchant;
    }

    public Boolean addMerchant(Map<String, Object> fileMap, Merchant merchant){

        try {
            merchantService.apply(fileMap, merchant);
        } catch (CoreException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
