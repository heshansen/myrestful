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

/**
* <p>BssBrandshopUserAccountController Test</p> 
* 
* @author SunXiaoYuan
* @since Dec 20, 2016
* @version 1.0
*/ 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
@Transactional
public class BssBrandshopUserAccountControllerTest extends BaseShiroTest {

    /**
    *
    * Method: getBrandshopUserAccount()
    *
    */
    @Test
    public void testGetBrandshopUserAccount() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders.get("/restful/bss/user/getAccount").accept(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
    *
    * Method: postCashAmount(@RequestParam(value="amount",required=false)BigDecimal amount)
    *
    */
    @Test
    public void testPostCashAmount() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders.post("/restful/bss/user/postAmount").param("amount", String.valueOf(10)).accept(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
    *
    * Method: getBrandshopUserAccountHistory(@RequestBody UserAccountHistoryPageParam pageParam)
    *
    */
    @Test
    public void testGetBrandshopUserAccountHistory() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders.get("/restful/bss/user/getAccountHistory").param("limit", String.valueOf(10)).param("start", String.valueOf(0)).accept(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }


} 
