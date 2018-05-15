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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** 
* BssUserUnsettleController Tester.
* @author <xianghui>
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
@Transactional
public class BssUserUnsettleControllerTest extends BaseShiroTest{


/** 
* 
* Method: getUnsettle(PageQueryModel pageQueryModel) 
* 
*/ 
@Test
public void testGetUnsettle() throws Exception {
    String url="/restful/bss/userUnsettle/getUnsettles";
    RequestBuilder request = null;
    request = get(url)
            .param("start","0")
            .param("page","1")
            .param("limit","6")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());

//TODO: Test goes here... 
} 

/** 
* 
* Method: memberDevUnliquidateds(PageQueryModel pageQueryModel) 
* 
*/ 
@Test
public void testMemberDevUnliquidateds() throws Exception {
    String url="/restful/bss/userUnsettle/getMemberDevs";
    RequestBuilder request = null;
    request = get(url)
            .param("start","0")
            .param("page","1")
            .param("limit","6")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print()); // print the request/response in the console
//TODO: Test goes here... 
} 

/** 
* 
* Method: rewActMemDevUnliquidateds(PageQueryModel pageQueryModel) 
* 
*/ 
@Test
public void testRewActMemDevUnliquidateds() throws Exception {
    String url="/restful/bss/userUnsettle/getRewActMemDevs";
    RequestBuilder request = null;
    request = get(url)
            .param("start","0")
            .param("page","1")
            .param("limit","6")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());

//TODO: Test goes here... 
} 

/** 
* 
* Method: rewActOrderUnliqudateds(PageQueryModel pageQueryModel) 
* 
*/ 
@Test
public void testRewActOrderUnliqudateds() throws Exception {
    String url="/restful/bss/userUnsettle/getRewActOrders";
    RequestBuilder request = null;
    request = get(url)
            .param("start","0")
            .param("page","1")
            .param("limit","6")
            .accept(MediaType.APPLICATION_JSON_UTF8);
    mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print());
//TODO: Test goes here... 
} 


} 
