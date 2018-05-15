package com.topbaby.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.topbaby.ecomerce.utils.JacksonUtils;
import com.topbaby.ecommerce.merchant.entity.Merchant;
import com.topbaby.ecommerce.merchant.entity.MerchantCategory;
import com.topbaby.ecommerce.merchant.service.MerchantService;
import com.topbaby.ecommerce.product.entity.Brand;
import com.topbaby.service.BrandCategoryService;
import com.topbaby.service.BrandRestService;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;
/**
 * 查商户品牌种类接口
 * Created by heshansen on 16-11-30.
 */
@Controller
@RequestMapping(value = "restful/merchantbrand")
public class MerchantBrandController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private BrandRestService brandRestService;
    @Autowired
    private BrandCategoryService brandCategoryService;
    /**
     * 查商户
     * @param merchantId 商户id
     * @return
     */
    @RequestMapping(value = "/merchant",method = RequestMethod.GET)
    @ResponseBody
    public Merchant getMerchant(@RequestParam(value = "merchantId", defaultValue = "151") Long merchantId){
        log.info("***getMerchant 开始。。。");
        Merchant merchant=merchantService.getModel(merchantId);
        return merchant;
    }
    /**
     * 查商户自主品牌
     * @param merchantId 商户id
     * @return
     */
    @RequestMapping(value = "/brand",method = RequestMethod.GET)
    @ResponseBody
    public String getMerchantBrand(
            @RequestParam(value = "merchantId") Long merchantId){
        log.info("***getBrand 开始。。。");
        if (merchantId==null){
            return "merchantId都不能为空！";
        }
        List<Map<String, Object>> result=null;
        try {
            result=brandRestService.getBrand(merchantId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonstr= "";
        try {
            jsonstr = JacksonUtils.getInstance().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonstr;
    }
    /**
     * 查商户自主品牌是否存在
     * @return
     * @param merchantId
     * @param brandSeq
     */
    @RequestMapping(value = "/brandexsit",method = RequestMethod.GET)
    @ResponseBody
    public String brandExsit(
            @RequestParam(value = "merchantId") Long merchantId,
            @RequestParam(value = "brandSeq") Long brandSeq){
        log.info("***brandExsit 开始。。。");
        if (merchantId==null || brandSeq==null){
            return "merchantId或brandSeq都不能为空！";
        }
        Boolean exsit=false;
        try {
            exsit=brandRestService.existBrand(merchantId,brandSeq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exsit+"";
    }
    /**
     * 查某商户某品牌某种类
     * @param
     * @return
     */
    @RequestMapping(value = "/cate",method = RequestMethod.GET)
    @ResponseBody
    public String getCate(
            @RequestParam(value = "merchantId") Long merchantId,
            @RequestParam(value = "brandSeq") Long brandSeq,
            @RequestParam(value = "catSeq") Long catSeq){
        log.info("***getCate开始。。。");
        if (merchantId==null || brandSeq==null || catSeq==null){
            return "merchantId或brandSeq或catSeq都不能为空！";
        }
        List<MerchantCategory> result=null;
        try {
            result=brandCategoryService.selectBrandCate(merchantId,brandSeq,catSeq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonstr= "";
        try {
            jsonstr = JacksonUtils.getInstance().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonstr;
    }
    /**
     * 查某商户某品牌某种类是否存在
     * @param
     * @return
     */
    @RequestMapping(value = "/cateExsit",method = RequestMethod.GET)
    @ResponseBody
    public String isExsitCate(
            @RequestParam(value = "merchantId") Long merchantId,
            @RequestParam(value = "brandSeq") Long brandSeq,
            @RequestParam(value = "catSeq") Long catSeq){
        log.info("***cateExsit开始。。。");
        if (merchantId==null || brandSeq==null || catSeq==null){
            return "merchantId或brandSeq或catSeq都不能为空！";
        }
        Boolean isCate=false;
        try {
            isCate=brandCategoryService.isBrandCate(merchantId,brandSeq,catSeq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isCate+"";
    }
    /**
     * 查品牌分页列表
     * @param
     * @return
     */
    @RequestMapping(value = "/brandPageList",method = RequestMethod.GET)
    @ResponseBody
    public String getBrandPageList(@RequestParam(value = "merchantId", defaultValue = "151") Long merchantId,PageQueryModel pageQueryModel){
        log.info("***查品牌分页列表开始。。。");
        //没有page参数时默认查1条数据
        if (pageQueryModel.getLimit()==0){
            pageQueryModel.setLimit(1);
        }
        PageModel<Brand> pageData=null;
        try {
            pageData = brandRestService.getBrandPageList(merchantId, pageQueryModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonstr= "";
        try {
            jsonstr = JacksonUtils.getInstance().writeValueAsString(pageData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonstr;
    }
}