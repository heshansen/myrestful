package com.topbaby.controller;

import com.topbaby.controller.base.BaseShiroTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * <p>BssOrderReturnController Test</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since Dec 20, 2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
@Transactional
public class BssOrderReturnControllerTest extends BaseShiroTest {

    /**
     * Method: getOrderReturnList(@RequestParam(value = "status", required = false) String status)
     */
    @Test
    public void testGetOrderReturnList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restful/bss/order/return/getList").accept(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(jsonPath("result").value(true));

    }

    /**
     * Method: verifyOrderReturn(@RequestParam(value = "id") Long id, @RequestParam(value = "status") String status, @RequestParam(value = "sendAddress", required = false) String sendAddress)
     */
    @Test
    public void testVerifyOrderReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restful/bss/order/return/putVerify?orderReturnID=17&status=1").accept(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(jsonPath("result").value(true));
    }

    @Test
    public void testReceiveOrderReturn() throws Exception {
        setSubject(subjectUnderTest);
        mockMvc.perform(MockMvcRequestBuilders.get("/restful/bss/order/return/putReceived?orderReturnID=23").accept(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(jsonPath("result").value(true));
    }

    /**
     * Method: receiveOrderReturn(@RequestParam(value = "id") Long id)
     */
    @Test
    public void testGetOrderReturnDetails() throws Exception {
        setSubject(subjectUnderTest);
        mockMvc.perform(MockMvcRequestBuilders.get("/restful/bss/order/return/getDetails?orderReturnID=17").accept(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(jsonPath("result").value(true));
    }


} 
