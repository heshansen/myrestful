package com.topbaby.controller;

import org.esbuilder.common.StringUtils;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * ForgotPasswordController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十二月 14, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
// do rollback
@Transactional
public class BssForgotPwControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;
    private MockHttpSession session;

    private String mobile = "18817595736";

    @Before
    public void before() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        this.session = new MockHttpSession();
    }


    /**
     * Method: sendValid(HttpServletRequest request, @RequestParam(value = "mobile", required = true) String mobile, @RequestParam(value = "captcha", required = false) String captcha,String flag)
     * 导购员发送短信测试（captcha=null代表跳过图形码验证！flag!=null代表屏蔽短信发送接口，防止频繁发送短信！）
     */
    @Test
    public void testSendValid() throws Exception {
        mockMvc.perform(post("/restful/bss/forgotPw/sendValid?flag=1&mobile=" + mobile)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result").value("true"))
                .andDo(print()); // print the request/response in the console
    }

    /**
     * Method: updatePassword(HttpServletRequest httpServletRequest, @RequestParam(value = "mobile", required = false) String mobile, @RequestParam(value = "vlCode", required = false) String vlCode, @RequestParam(value = "newPassword", required = false) String newPassword, @RequestParam(value = "newPasswordRepeat", required = false) String newPasswordRepeat)
     * 导购员登录密码更新（必须先运行测试testSendValid：生成动态码放入session；不用验证图形码，且无须发送短信）
     */
    @Test
    public void testUpdatePassword() throws Exception {
        //生成短信动态码,存入session
        this.session=(MockHttpSession)this.getVlCodeSession();
        //获取session中动态码
        String vlcode = (String) this.session.getAttribute("vlCode");
//        String vlcode ="1111";//result=false
                String url="/restful/bss/forgotPw/updatePassword?mobile=" + mobile + "&vlCode=" + vlcode + "&newPasswordRepeat=123456";
        if (!StringUtils.isNullOrEmpty(vlcode)) {
            mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON_UTF8).session(this.session))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.result").value("true"))
                    .andDo(print()); // print the request/response in the console
        }
    }

    /**
     * 获取动态码session
     *
     * @return
     * @throws Exception
     */
    private HttpSession getVlCodeSession() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/restful/bss/forgotPw/sendValid?flag=1&mobile=" + mobile)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result").value("true"))
                .andDo(print())// print the request/response in the console
                .andReturn();
        return result.getRequest().getSession();
    }

    /**
     * Method: validPhone(@RequestParam(value = "mobile", defaultValue = "null") String mobile)
     * 测试此导购员号码是否已经维护
     *
     * @throws Exception
     */
    @Test
    public void testValidPhone() throws Exception {
        mockMvc.perform(get("/restful/bss/forgotPw/validPhone?mobile=" + mobile)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.result").value("true"))
                .andDo(print()); // print the request/response in the console
    }



}