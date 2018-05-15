package com.topbaby.service;


import com.topbaby.ecommerce.merchant.entity.MerchantCategory;

import java.util.List;

/**
 * 品牌种类相关处理
 * Created by heshansen on 16-11-29.
 */
public interface BrandCategoryService {

    List<MerchantCategory> selectBrandCate(Long merchantSeq, Long brandSeq, Long catSeq) throws Exception;
    Boolean isBrandCate(Long merchantSeq,Long brandSeq,Long catSeq) throws Exception;

}
