package com.topbaby.controller;

import com.topbaby.ecommerce.brandshop.entity.Brandshop;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.service.BrandshopService;
import com.topbaby.entity.BrandshopUserCodeMsgInfo;
import com.topbaby.entity.BrandshopUserProductsInfo;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BrandshopRestService;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.timon.security.IUser;
import org.timon.security.authc.IAuthenticationService;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xianghui
 * 导购员登陆,查看上架的商品
 * 修改商品的库存,导购员二维码
 */
@Controller
@RequestMapping(value="/restful/bss/brandshopUser")
public class BssUserController {

    @Autowired
    private IAuthenticationService authenticationService ;
    @Autowired
    private BrandshopRestService brandshopRestService ;
    @Autowired
    private BrandshopService brandshopService ;

    @RequestMapping(value="/login",method= {RequestMethod.POST})
    @ResponseBody
    public BaseResponseMsg login()
    {
//        if (this.authenticationService != null) {
            IUser user = this.authenticationService.getAuthenticatedUser();
            if (user != null && !user.isLogout()) {
                return new BaseResponseMsg(true,"登录成功");
            }
//        }
        return new BaseResponseMsg(false,"账号或者密码不正确");
    }


    /**导购员的二维码,给会员扫码,然后跳转到注册页面*/
    @RequestMapping(value="/tranCodeMsg",method=RequestMethod.GET)
    @ResponseBody
    public BaseResponseMsg tranCodeMsg() {
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        if(brandshopUser!=null) {
            /**前台扫码成功后要跳转到会员注册页面的url*/
            String targetUrl = "http://www.61topbaby.com/evercos/member/register/index.html";
            BrandshopUserCodeMsgInfo brandshopUserCodeMsgInfo = new BrandshopUserCodeMsgInfo();
            brandshopUserCodeMsgInfo.setBrandshopUserId(brandshopUser.getId());
            brandshopUserCodeMsgInfo.setTargetUrl(targetUrl);
            return brandshopUserCodeMsgInfo ;
        }
        return new BaseResponseMsg(false,"未知的错误") ;
    }

    /**上架的商品list*/
    @RequestMapping(value="/getProducts", method=RequestMethod.GET)
    @ResponseBody
    public BaseResponseMsg getProductList(PageQueryModel queryModel, @RequestParam(value="type",required=false) String type,
                                          @RequestParam(value="productStoNo",required=false) String productStoNo){
        BrandshopUser brandshopUser = (BrandshopUser) authenticationService.getAuthenticatedUser();
        Map<String, Object> param = new HashMap<String, Object>();
        /**1表示上架,0表示下架(注销)*/
        param.put("status", "1");
        /**门店id*/
        param.put("brandshopIdd", brandshopUser.getBrandshopSeq());
        /** o代表商品货号*/
        if ("0".equals(type)) {
            param.put("productStoNo", productStoNo);
        } else  {
            /**其他代表商品名称*/
            param.put("name", productStoNo);
        }
        BrandshopUserProductsInfo brandshopUserProductsInfo =  null;
        try {
            brandshopUserProductsInfo = (BrandshopUserProductsInfo) brandshopRestService.getProductList(queryModel,param);
        } catch (CoreException e) {
            e.printStackTrace();
            return new BaseResponseMsg(false,"错误");
        }
        Brandshop brandshop = brandshopService.getModel(brandshopUser.getBrandshopSeq());
        if(brandshopUserProductsInfo!=null&&brandshop!=null) {
            brandshopUserProductsInfo.setBrandshopName(brandshop.getName());
        }

        return brandshopUserProductsInfo ;
    }

    /**修改商品的上架数量.id为商品id,stock为库存,
     * updateNo为1时只修改被选中的商品的库存,其他为批量修改同款商品库存 */
    @RequestMapping(value="/putStock",method=RequestMethod.PUT)
    @ResponseBody
    public BaseResponseMsg updateStock(@RequestParam(value="id",required=false) Long id , @RequestParam(value="stock",required=false) int stock ,
                                       @RequestParam(value="updateNo",required=false) String updateNo) {
        BaseResponseMsg baseResponseMsg = null;
        if (id != null && stock >= 0) {

            try {
                baseResponseMsg = brandshopRestService.updateRepCount(id, stock, updateNo);
                return baseResponseMsg;
            } catch (CoreException e) {

                return new BaseResponseMsg(false, "修改出错");
            }
        }
        return  new BaseResponseMsg(false, "修改出错");
    }

}



