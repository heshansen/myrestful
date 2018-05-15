package com.topbaby.controller; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* BssUserAccountSetController Tester.
*
* @author <Authors name>
* @since <pre>十二月 20, 2016</pre>
* @version 1.0
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
// do rollback
@Transactional
public class BssUserAccountSetControllerTest {
@Autowired
    private WebApplicationContext ctx;
    private MockMvc mockMvc;
@Before
public void before() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
}

@After
public void after() throws Exception {
}

/**
*
* Method: index()
*
*/
@Test
public void testIndex() throws Exception {
    String url="/restful/bss/account/getDetail";
    mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.brandshopSeq").value("254"))
            .andExpect(jsonPath("$.salesName").value("刘巍"))
            .andDo(print()); // print the request/response in the console
} 

/** 
* 
* Method: telUpdate(BrandshopUser bsUser, @RequestParam(value = "idCardFrontImg", required = false) String idCardFrontImg, @RequestParam(value = "idCardBackImg", required = false) String idCardBackImg) 
* 
*/ 
@Test
//@Rollback(value = false)
public void testTelUpdate() throws Exception {
    String url="/restful/bss/account/postUpdate";
    mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.result").value("true"))
        .andDo(print()); // print the request/response in the console
} 

/** 
* 
* Method: list(@RequestParam(value = "status", required = false) String status, PageQueryModel pageQueryModel) 
* 
*/ 
@Test
public void testList() throws Exception {
    String url="/restful/bss/account/verifyList";
    mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.totalRecord").value("0"))
            .andDo(print()); // print the request/response in the console
} 


} 
