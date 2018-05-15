
package com.topbaby.controller;

import com.topbaby.controller.base.BaseShiroTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>BssOrderController Test</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since Dec 16, 2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
@Transactional
public class BssOrderControllerTest extends BaseShiroTest {

    /**
     * Method: confirmMemberReceived(@RequestParam(value = "orderID", required = false) Long orderID)
     */
    @Test
    public void testConfirmMemberReceived(){
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/restful/bss/order/confirmOrder?id=114").accept(MediaType.APPLICATION_JSON_UTF8))
                                              .andExpect(status().isOk())
                                              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                              .andExpect(jsonPath("result").value(true))
                                              .andExpect(jsonPath("$.itemList[0].number").value(1))
                                              .andExpect(jsonPath("itemList").isArray());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOrderReceived() {
        try {

            mockMvc.perform( MockMvcRequestBuilders.get("/restful/bss/order/orderReceived?id="+"1238").accept(MediaType.APPLICATION_JSON_UTF8))
                                              .andExpect(status().isOk())
                                              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                              .andExpect(jsonPath("result").value(true))
                                              .andExpect(jsonPath("message").value("success"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Method: getList(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "orderId", required = false) String orderId, PageQueryModel pageQueryModel)
     *
     */
    @Test
    public void testGetList() throws Exception {
        String url="/restful/bss/order/getList";
        mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.totalRecord").value("344"))
                .andDo(print()); // print the request/response in the console
    }

    /**
     *
     * Method: getList(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "orderId", required = false) String orderId, PageQueryModel pageQueryModel)
     *
     */
    @Test
    public void testGetListStatus() throws Exception {
        String url="/restful/bss/order/getList?status=1";
        mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.totalRecord").value("96"))
                .andDo(print()); // print the request/response in the console
    }


    /**
     *
     * Method: getList(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "orderId", required = false) String orderId, PageQueryModel pageQueryModel)
     *
     */
    @Test
    public void testListOrderId() throws Exception {
        String url="/restful/bss/order/getList?status=1&orderId=20160419";
        mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.totalRecord").value("2"))
                .andDo(print()); // print the request/response in the console
    }

} 
