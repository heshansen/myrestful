package com.topbaby.controller.base;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.timon.security.shiro.authc.token.DefaultUsernamePasswordToken;

/**
 * <p>测试基础类</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-23
 */
public class BaseShiroTest extends AbstractShiroTest {

    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected DefaultWebSecurityManager securityManager;

    protected MockMvc mockMvc;
    protected Subject subjectUnderTest;

    @Before
    public void before(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        securityManager.setSessionManager(new DefaultWebSessionManager());

        setSecurityManager(securityManager);

        subjectUnderTest = getSubject();

        AuthenticationToken token = new DefaultUsernamePasswordToken("MQD001","123123");
        subjectUnderTest.login(token);
        setSubject(subjectUnderTest);
    }

    @After
    public void after(){
        clearSubject();
    }
}
