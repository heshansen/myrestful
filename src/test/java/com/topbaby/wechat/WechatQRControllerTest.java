package com.topbaby.wechat;

import com.topbaby.controller.base.BaseShiroTest;
import com.topbaby.entity.AccessKeyEntity;
import com.topbaby.service.AccessKeyService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>WechatQRController Test</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since Jan 4, 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
@Transactional(value = "restTXManager")
public class WechatQRControllerTest extends BaseShiroTest {

    /**
     * Method: getQRCodeUrl(@RequestParam(name = "userId")Long userId, @RequestParam(name = "type")String type)
     */
    @Test
    public void testGetQRCodeUrl() throws Exception {
        String url = "/restful/wechat/qrCode/getQRCodeUrl";
        mockMvc.perform(get(url).param("userId", "1").param("type", "U")
                                          .accept(MediaType.APPLICATION_JSON_UTF8))
                                          .andExpect(status().isOk())
                                          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                          .andDo(print());
    }



} 
