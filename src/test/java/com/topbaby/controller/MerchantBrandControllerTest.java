package com.topbaby.controller;

import com.topbaby.service.BrandCategoryService;
import com.topbaby.service.BrandRestService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/** 
* MerchantBrandController Tester. 
* 
* @author <Authors name> 
* @since <pre>十一月 30, 2016</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
@Transactional
public class MerchantBrandControllerTest {


    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private BrandRestService brandRestService;

/** 
* 
* Method: getCate(@RequestParam(value = "name", defaultValue = "testCate") String name) 
* 
*/ 
@Test
public void testGetCate() {
    long merchantSeq=151;
    long brandSeq=28;
    long catSeq=60;
    try {
        Assert.assertEquals(true,brandCategoryService.isBrandCate(merchantSeq,brandSeq,catSeq));
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Test
    public void testBrandExsit(){
    long merchantSeq=151;
    long brandSeq=28;
    try {
        Assert.assertEquals(true,brandRestService.existBrand(merchantSeq,brandSeq));
    } catch (Exception e) {
        e.printStackTrace();
    }
}



} 
