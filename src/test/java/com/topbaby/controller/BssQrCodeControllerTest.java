package com.topbaby.controller; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/** 
* BssQRCodeController Tester. 
* 
* @author <Authors name> 
* @since <pre>十二月 19, 2016</pre> 
* @version 1.0 
*/ 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
// do rollback
@Transactional
public class BssQrCodeControllerTest {
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
* Method: getBsPicture()
*
*/
@Test
public void testGetBsPicture() throws Exception {
    mockMvc.perform(get("/restful/bss/qrCode/getBsPicture")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.result").value("true"))
        .andExpect(jsonPath("$.brandshopName").value("MQD上海五角场店"))
        .andDo(print()); // print the request/response in the console
}

/**
*
* Method: getQrCode()
* 
*/ 
@Test
public void testGetQrCode() throws Exception {
    mockMvc.perform(get("/restful/bss/qrCode/getQrCode")
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.result").value("true"))
            .andDo(print()); // print the request/response in the console
} 


} 
