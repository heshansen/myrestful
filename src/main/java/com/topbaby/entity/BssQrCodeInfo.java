package com.topbaby.entity;

import com.topbaby.entity.base.BaseResponseMsg;

/**
 * Created by heshansen on 16-12-15.
 */
public class BssQrCodeInfo extends BaseResponseMsg {
    private Long brandshopId;
    private String brandshopName;
    private String imageUrl;
    private String qrCodeUrl;

    public Long getBrandshopId() {
        return brandshopId;
    }

    public void setBrandshopId(Long brandshopId) {
        this.brandshopId = brandshopId;
    }

    public String getBrandshopName() {
        return brandshopName;
    }

    public void setBrandshopName(String brandshopName) {
        this.brandshopName = brandshopName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

}
