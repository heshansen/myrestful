package com.topbaby.controller;

import com.topbaby.ecomerce.utils.JacksonUtils;
import com.topbaby.ecommerce.product.entity.Product;
import com.topbaby.ecommerce.product.entity.ProductImageData;
import com.topbaby.ecommerce.product.entity.ProductSKU;
import com.topbaby.entity.ProductInfo;
import com.topbaby.entity.ProductSKUInfo;
import com.topbaby.service.ProductRestService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * details：商品接口测试
 * Created by sxy on 16-11-30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*.xml"})
@WebAppConfiguration
@Transactional
public class ProductRestControllerTest {
    @Autowired
    ProductRestService productRestService;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    Product product = new Product();

    long merchantID = 124;
    String stockNo = "test6666";

    @Before
    public void init() {
        String productJsonStr = "{\"id\":343,\"createDate\":null,\"updateDate\":null,\"createTime\":null,\"updateTime\":null,\"catSeq\":247,\"merchantSeq\":null,\"detailFielSeq\":3989,\"companyName\":null,\"cateName\":\"奶瓶\",\"brandSeq\":2,\"brandName\":\"布朗博士\",\"name\":\"孙先生的测试\",\"productNo\":null,\"stockNo\":\"sxy6677\",\"bar\":\"sxy6677\",\"price\":66,\"deliverPrice\":null,\"minShopPrice\":null,\"maxShopPrice\":null,\"lowestPrice\":40,\"highestPrice\":188,\"lowestBrandshopPrice\":30,\"highestBrandshopPrice\":55,\"status\":null,\"publishDate\":1444635571000,\"imgIdLs\":null,\"pAttrValues\":null,\"imageId\":3976,\"salesVolume\":null,\"isShow\":\"2\",\"prodType\":\"0\",\"offerDelivery\":\"3\"}";
        try {
            product = JacksonUtils.getInstance().readValue(productJsonStr,Product.class);
            mvc = MockMvcBuilders.webAppContextSetup(context).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void verifyProduct() {
        try {
            Assert.assertEquals(true, productRestService.verifyProductStockNo(stockNo,merchantID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addProductTest() {
        ProductImageData productImageData = new ProductImageData();
        try {
            Assert.assertEquals(true,productRestService.addProduct(product,"",productImageData,"",merchantID,null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verifySKUTest() {
        String productSKUAttrValues = "[{\"prodSeq\":\"11\",\"price\":\"666.00\",\"bar\":\"5094121\",\"attrData\":[{\"id\":\"795\",\"value\":\"绿色色\"},{\"id\":\"796\",\"value\":\"240mm\"}]}]";

        try {
            Assert.assertEquals(true,productRestService.verifyProductSKU("11",productSKUAttrValues));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSKUTest() {
        String productSKUAttrValues = "[{\"prodSeq\":\"11\",\"price\":\"666.00\",\"bar\":\"5094121\",\"attrData\":[{\"id\":\"795\",\"value\":\"绿色\"},{\"id\":\"796\",\"value\":\"666mm\"}]}]";
        try {
            productRestService.addProductSKU(String.valueOf(11), productSKUAttrValues, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void productControllerTest() {
        try {
            ResultActions resultActions = mvc.perform(get("/restful/getProduct").accept(MediaType.parseMediaType("application/json;charset=UTF-8")));

//            System.out.println(resultActions.andDo(MockMvcResultHandlers.print()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback
    public void verifyAndAddProductTest() {

        String prodcutInfoStr = "{\"product\":{\"catSeq\":247,\"brandSeq\":2,\"name\":\"孙先生的测试数据\",\"stockNo\":\"666777\",\"bar\":\"666777\",\"price\":138,\"prodType\":\"0\",\"offerDelivery\":\"3\"},\"productImageData\":{\"productImg1\":\"1\",\"productImg2\":null,\"productImg3\":null,\"productImg4\":null,\"productImg5\":null,\"productImg1Name\":\"1\",\"productImg2Name\":null,\"productImg3Name\":null,\"productImg4Name\":null,\"productImg5Name\":null},\"productAttrValues\":\"[{\\\"id\\\":\\\"333\\\",\\\"value\\\":\\\"冬季\\\"},{\\\"id\\\":\\\"334\\\",\\\"value\\\":\\\"高帮\\\"},{\\\"id\\\":\\\"335\\\",\\\"value\\\":\\\"松紧带\\\"}]\",\"productDetailHtml\":null,\"productSKUAttrValues\":\"[{\\\"prodSeq\\\":null,\\\"price\\\":\\\"666.00\\\",\\\"bar\\\":\\\"666777\\\",\\\"attrData\\\":[{\\\"id\\\":\\\"796\\\",\\\"value\\\":\\\"666mm\\\"},{\\\"id\\\":\\\"795\\\",\\\"value\\\":\\\"红色\\\"}]}]\",\"prodInfos\":[1,2]}\n";

//        String prodcutInfoStr = "{\"product\":{\"catSeq\":247,\"brandSeq\":2,\"name\":\"孙先生的牛奶\",\"stockNo\":\"666777\",\"bar\":\"666777\",\"price\":138},\"productImageData\":{\"productImg1\":\"1\",\"productImg2\":null,\"productImg3\":null,\"productImg4\":null,\"productImg1Name\":\"1\",\"productImg2Name\":null,\"productImg3Name\":null,\"productImg4Name\":null},\"productAttrValues\":\"[{\\\"id\\\":\\\"333\\\",\\\"value\\\":\\\"冬季\\\"},{\\\"id\\\":\\\"334\\\",\\\"value\\\":\\\"高帮\\\"},{\\\"id\\\":\\\"335\\\",\\\"value\\\":\\\"松紧带\\\"}]\",\"productDetailHtml\":null,\"productSKUAttrValues\":\"[{\\\"prodSeq\\\":null,\\\"price\\\":\\\"666.00\\\",\\\"bar\\\":\\\"666777\\\",\\\"attrData\\\":[{\\\"id\\\":\\\"796\\\",\\\"value\\\":\\\"666mm\\\"},{\\\"id\\\":\\\"795\\\",\\\"value\\\":\\\"红色\\\"}]}]\"}";

        List<ProductInfo> productInfos = new ArrayList<ProductInfo>();

        try {
            ProductInfo productInfoA = JacksonUtils.getInstance().readValue(prodcutInfoStr,ProductInfo.class);
            ProductInfo productInfoB = JacksonUtils.getInstance().readValue(prodcutInfoStr,ProductInfo.class);
            productInfos.add(productInfoA);
            productInfos.add(productInfoB);
            productRestService.verifyAndAddProduct(productInfos,merchantID,null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback
    public void updateProduct() {
        ProductImageData productImageData = new ProductImageData();
        product.setName(new Date().toString());
        try {
            productRestService.updateProduct(product,"",productImageData,"",merchantID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addProductSKUTest() throws Exception {
        String productSKUAttrValues = "[{\"prodSeq\":\"11\",\"price\":\"666.00\",\"bar\":\"5094121\",\"attrData\":[{\"id\":\"795\",\"value\":\"哈哈\"},{\"id\":\"796\",\"value\":\"20mm\"}]}]";

        List<String> productSKUStrList = new ArrayList<String>();

        productSKUStrList.add(productSKUAttrValues);
        productRestService.verifyAndAddProductSKU(productSKUStrList,null);
    }

    @Test
    public void putProductSKUTest() throws Exception {

        List<ProductSKUInfo> productSKUInfoList = new ArrayList<ProductSKUInfo>();

        ProductSKUInfo productSKUInfo = new ProductSKUInfo();

        ProductSKU productSKU = new ProductSKU();

        productSKU.setId(Long.valueOf(9));
        productSKU.setBar(String.valueOf(5094121));
        productSKU.setPrice(BigDecimal.valueOf(666));

        productSKUInfo.setProductSKU(productSKU);

        productSKUInfoList.add(productSKUInfo);

        productRestService.verifyAndPutProductSKU(productSKUInfoList);
    }
}
