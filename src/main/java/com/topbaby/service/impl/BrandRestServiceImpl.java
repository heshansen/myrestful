package com.topbaby.service.impl;

import com.topbaby.ecommerce.product.entity.Brand;
import com.topbaby.ecommerce.product.service.BrandService;
import com.topbaby.service.BrandRestService;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heshansen on 16-11-30.
 */
@Service
public class BrandRestServiceImpl implements BrandRestService {
    @Autowired
    private BrandService brandService;


    /**
     * 判断商户品牌是否存在
     * @param merchantSeq
     * @param brandSeq
     * @return
     * @throws Exception
     */
    public Boolean existBrand(Long merchantSeq, Long brandSeq) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchantSeq",  merchantSeq);
        param.put("brandSeq", brandSeq);
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        try {
            list=brandService.kvData(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list!=null && list.size()>0){
            return true;
        }
        return false;
    }

    /**
     * 查商户自主品牌
     * @param merchantSeq
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getBrand(Long merchantSeq) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchantSeq",  merchantSeq);
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        try {
            list=brandService.kvData(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PageModel<Brand> getBrandPageList(Long merchantSeq, PageQueryModel pageQueryModel) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchantSeq",  merchantSeq);
        PageModel<Brand> pageData = brandService.getPageList(param, pageQueryModel);

        return pageData;
    }
}
