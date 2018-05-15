package com.topbaby.controller;

import com.topbaby.common.TopConstants;
import com.topbaby.ecommerce.merchant.service.MerchantCategoryService;
import com.topbaby.entity.base.BaseResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * details： 查询商品种类信息
 * Created by sxy on 16-12-9.
 */
@Controller
@RequestMapping(value = "restful/catalog")
public class CatalogController {

    @Autowired
    MerchantCategoryService merchantCategoryService;

    /**
     * <p>根据品牌获取种类（1\2\3级）</p>
     *
     * @param brandID  品牌
     * @param parentID 父类别ID（一级种类parentID = 0）
     * @param level    种类级别（0-一级种类，1-二级种类，2-三级种类）
     * @return 商品信息Json串
     */
    @RequestMapping(value = "/getCatalogByBrand", method = RequestMethod.GET)
    @ResponseBody
    public Object getCatalogsByBrandID(@RequestParam(value = "brandID") Long brandID, @RequestParam(value = "parentID", required = false) Long parentID, @RequestParam(value = "level", defaultValue = "2") String level) {
        if (brandID == null) {
            return new BaseResponseMsg(false, TopConstants.BRANDIDISNULL);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("brandId", brandID);
        param.put("parentSeq", parentID);
        param.put("level", level);
        param.put("status", TopConstants.COMMONATTRIBUTESTATUS);

        return merchantCategoryService.kvData(param);
    }

}
