package com.topbaby.controller;

import com.topbaby.controller.base.BaseShiroTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** 
* BssUserController Tester. 
* 
* @author <xianghui>
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
@Transactional

public class BssUserControllerTest extends BaseShiroTest {

/** 
* 
* Method: login() 
* 
*/ 
@Test
public void testLogin() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: tranCodeMsg() 
* 
*/ 
@Test
public void testTranCodeMsg() throws Exception {
    String url="/restful/bss/brandshopUser/tranCodeMsg";
    mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());
//TODO: Test goes here... 
} 

/** 
* 
* Method: getProductList(PageQueryModel queryModel, @RequestParam(value="type",required=false) String type, @RequestParam(value="productStoNo",required=false) String productStoNo) 
*   获取上架的商品
*/ 
@Test
public void testGetProductList() throws Exception {
    String url="/restful/bss/brandshopUser/getProducts?start=0&limit=5&page=1&type=0";
    mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());

//TODO: Test goes here... 
} 

/** 
* 
* Method: updateStock(@RequestParam(value="id",required=false) Long id, @RequestParam(value="stock",required=false) int stock, @RequestParam(value="updateNo",required=false) String updateNo) 
* 更新库存
*/ 
@Test
public void testUpdateStock() throws Exception {
    String url="/restful/bss/brandshopUser/putStock";
    RequestBuilder request = null;
    /**依次为产品id,库存,更新编号(1代表只更新1个)*/
    request = put(url)
            .param("id","293")
            .param("stock","5")
            .param("updateNo","1")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());


//TODO: Test goes here... 
} 


} 
