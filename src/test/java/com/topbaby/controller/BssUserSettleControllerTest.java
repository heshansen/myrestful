package com.topbaby.controller; 

import com.topbaby.controller.base.BaseShiroTest;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** 
* BssUserSettleController Tester. 
* 
* @author <xianghui>
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
@Transactional
public class BssUserSettleControllerTest extends BaseShiroTest{

/** 
* 
* Method: getCashes(PageQueryModel pageQueryModel, @RequestParam(value="startTime",required=false) String startTime)
*/ 
@Test
public void testGetCashes() throws Exception {
    String url="/restful/bss/userSettle/getCashes";
    RequestBuilder request = null;
    request = get(url)
            .param("start","0")
            .param("page","1")
            .param("limit","4")
            .param("startTime","")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());

//TODO: Test goes here... 
} 

/** 
* 
* Method: getSettles(PageQueryModel pageQueryModel, @RequestParam(value="startTime",required=false) String startTime, @RequestParam(value="endTime",required=false) String endTime) 
* 
*/ 
@Test
public void testGetSettles() throws Exception {
    String url="/restful/bss/userSettle/getSettles";
    RequestBuilder request = null;
    request = get(url)
            .param("start","0")
            .param("page","1")
            .param("limit","3")
            .param("startTime","2015-10-2")
            .param("endTime","2016-11-11")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());


//TODO: Test goes here... 
} 

/** 
* 
* Method: getSettleOrders(PageQueryModel pageQueryModel, @RequestParam(value="id",required = false) Long id) 
* 
*/ 
@Test
public void testGetSettleOrders() throws Exception {
    String url="/restful/bss/userSettle/getSettleOrders";
    RequestBuilder request = null;
    request = get(url)
            .param("start","0")
            .param("page","1")
            .param("limit","6")
            .param("id","251")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());

//TODO: Test goes here... 
} 

/** 
* 
* Method: getMemDevSettles(@RequestParam(value="id",required=false) Long id, PageQueryModel pageQueryModel) 
* 
*/ 
@Test
public void testGetMemDevSettles() throws Exception {
    String url="/restful/bss/userSettle/getMemDevSettles";
    RequestBuilder request = null;
    request = get(url)
            .param("start","0")
            .param("page","1")
            .param("limit","6")
            .param("id","253")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());


//TODO: Test goes here... 
} 

/** 
* 
* Method: getRewActSettles(Long id, PageQueryModel pageQueryModel) 
* 
*/ 
@Test
public void testGetRewActSettles() throws Exception {
    String url="/restful/bss/userSettle/getRewActSettles";
    RequestBuilder request = null;
    request = get(url)
            .param("start","0")
            .param("page","1")
            .param("limit","6")
            .param("id","251")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());

//TODO: Test goes here... 
} 


} 
