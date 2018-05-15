package com.topbaby.service.impl;

import com.topbaby.ecommerce.merchant.entity.MerchantCategory;
import com.topbaby.ecommerce.merchant.service.MerchantCategoryService;
import com.topbaby.service.BrandCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 品牌种类相关处理
 * Created by heshansen on 16-11-29.
 */
@Service
    public class BrandCategoryServiceImpl implements BrandCategoryService{

    @Autowired
    protected MerchantCategoryService merchantCategoryService;
    /**
     * 查询某一商户下某一品牌的某一种类
     * @return jsonString
     */

    public List<MerchantCategory> selectBrandCate(Long merchantSeq,Long brandSeq,Long catSeq){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchantSeq", merchantSeq);
        param.put("brandSeq", brandSeq);
        param.put("catSeq3", catSeq);
        param.put("status","E");
        List<MerchantCategory> authList = null;
        try {
            authList = merchantCategoryService.getList(param);
        }catch (Exception e){
            e.printStackTrace();
        }

        return authList;
    }

    /**
     * 判断某商户品牌种类是否存在
     * @param merchantSeq
     * @param brandSeq
     * @param catSeq
     * @return
     * @throws Exception
     */
    public Boolean isBrandCate(Long merchantSeq, Long brandSeq, Long catSeq) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchantSeq", merchantSeq);
        param.put("brandSeq", brandSeq);
        param.put("catSeq3", catSeq);
        param.put("status","E");
        List<MerchantCategory> authList = null;
        try {
            authList = merchantCategoryService.getList(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(authList!=null && authList.size()>0){
            return true;
        }
        return false;
    }

}
