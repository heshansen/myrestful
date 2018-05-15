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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** 
* BssGiftController Tester. 
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
public class BssGiftControllerTest { 
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
* 测试已使用赠品，接口默认userId=1127
* Method: getList(@RequestParam(value = "createDate", required = false) String createDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "type", required = false) String type, PageQueryModel pageQueryModel, HttpServletRequest httpServletRequest)
* 
*/ 
@Test
public void testList() throws Exception {
    String url="/restful/bss/gift/getList?status=1";
    mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.totalRecord").value("1"))
            .andExpect(jsonPath("$.dataList[0].giftName").value("英氏-真空杯"))
            .andExpect(jsonPath("$.dataList[0].brandshopName").value("英氏(上海凯德龙之梦虹口自营店)"))
            .andDo(print()); // print the request/response in the console
}


    /**
     *　测试未使用赠品，接口默认userId=677
     * Method: getList(@RequestParam(value = "createDate", required = false) String createDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "status", required = false) String status, PageQueryModel pageQueryModel, HttpServletRequest httpServletRequest)
     *
     */
    @Test
    public void testList1() throws Exception {
        String url="/restful/bss/gift/getList?status=0";
        mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.totalRecord").value("9"))
                .andExpect(jsonPath("$.dataList[0].giftName").value("TOP-1001-1"))
                .andExpect(jsonPath("$.dataList[0].brandshopName").value("1001夜杭州旗舰店"))
                .andDo(print()); // print the request/response in the console
    }


    /**
     *　测试按日期未使用赠品，接口默认userId=677
     * Method: list(@RequestParam(value = "createDate", required = false) String createDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "status", required = false) String status, PageQueryModel pageQueryModel, HttpServletRequest httpServletRequest)
     *
     */
    @Test
    public void testListCreate() throws Exception {
        String url="/restful/bss/gift/getList?status=0&createDate=2016-08-11 21:06:45";
        mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.totalRecord").value("1"))
                .andExpect(jsonPath("$.dataList[0].giftName").value("TOP-1001-1"))
                .andExpect(jsonPath("$.dataList[0].brandshopName").value("1001夜杭州旗舰店"))
                .andDo(print()); // print the request/response in the console
    }

    /**
     * 测试按日期查询已使用赠品，接口默认userId=1127
     * Method: list(@RequestParam(value = "createDate", required = false) String createDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "status", required = false) String status, PageQueryModel pageQueryModel, HttpServletRequest httpServletRequest)
     *
     */
    @Test
    public void testListEndDate() throws Exception {
        String endDate="2016-09-14 18:55:41";
        String url="/restful/bss/gift/getList?status=1&endDate="+endDate;
        mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.totalRecord").value("1"))
                .andExpect(jsonPath("$.dataList[0].giftName").value("英氏-真空杯"))
                .andExpect(jsonPath("$.dataList[0].brandshopName").value("英氏(上海凯德龙之梦虹口自营店)"))
                .andDo(print()); // print the request/response in the console
    }

} 
