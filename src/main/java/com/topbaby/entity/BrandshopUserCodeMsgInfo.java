package com.topbaby.entity;

import com.topbaby.entity.base.BaseResponseMsg;


    /**
     * Created by xianghui on 16-12-16.
     * * details： 导购员二维码信息
     */
    public class BrandshopUserCodeMsgInfo extends BaseResponseMsg {
        /** 导购员id*/
        private Long brandshopUserId ;
        /** 会员注册页面*/
        private String targetUrl;

        public BrandshopUserCodeMsgInfo() {
            super();
        }


        public Long getBrandshopUserId() {
            return brandshopUserId;
        }

        public void setBrandshopUserId(Long brandshopUserId) {
            this.brandshopUserId = brandshopUserId;
        }


        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }
    }



