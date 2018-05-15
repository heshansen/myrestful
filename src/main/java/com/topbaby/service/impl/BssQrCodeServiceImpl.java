package com.topbaby.service.impl;

import com.topbaby.ecommerce.brandshop.entity.Brandshop;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.service.BrandshopService;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserService;
import com.topbaby.ecommerce.common.entity.UploadFile;
import com.topbaby.ecommerce.common.service.IUploadFileService;
import com.topbaby.entity.BssQrCodeInfo;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssQrCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>Title: 门店二维码/p>
 *
 * @Author heshansen
 * @Date 2016-12-22 19:31.
 * @Version V1.0
 */
@Service
public class BssQrCodeServiceImpl implements BssQrCodeService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    //二维码生成路径
    @Value("${emall.brandshop.qrcode.url:http://www.61topbaby.com/evercos/index.html}")
    private String qrCodeUrl;
    @Value("${emall.brandshop.picture.path:http://www.61topbaby.com/everbss/}")
    private String imageUrl;

    @Autowired
    private BrandshopUserService brandshopUserService;
    @Autowired
    private BrandshopService brandshopService;
    @Autowired
    private IUploadFileService uploadFileService;

    /**
     * 获取门店名称、图片url、二维码url
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    public BssQrCodeInfo getBsPicture(Long userId) throws Exception {
        BssQrCodeInfo qrCode = new BssQrCodeInfo();
        qrCode.setResult(false);
        BrandshopUser brandshopUser = brandshopUserService.getModel(userId);
        if (brandshopUser != null) {
            Long brandshopId = brandshopUser.getBrandshopSeq();
            Brandshop brandshop = brandshopService.getModel(brandshopId);
            if (brandshop != null) {
                log.info("门店二维码查询结果：由门店id[" + brandshopId + "]查出门店名称＝" + brandshop.getName());
                qrCode.setBrandshopName(brandshop.getName());
                qrCode.setQrCodeUrl(qrCodeUrl + "?sourceId=" + userId);
                //根据门店图片ID,查看图片文件信息
                UploadFile imageFile = uploadFileService.getModel(brandshop.getImage());
                if (imageFile != null) {
                    qrCode.setResult(true);
                    qrCode.setImageUrl(imageUrl + imageFile.getFileUrl() + "/" + imageFile.getFileName());
                } else {
                    qrCode.setMessage("此门店形象图片信息不存在！");
                }
            } else {
                qrCode.setMessage("门店信息不存在！");
            }
        } else {
            qrCode.setMessage("系统没有维护此导购员信息！");
        }
        return qrCode;
    }

    public BaseResponseMsg getQrCode(Long userId) throws Exception {
        BrandshopUser brandshopUser = brandshopUserService.getModel(userId);
        if (brandshopUser != null) {
            String appUrl=qrCodeUrl+ "?sourceId=" +userId;
            return new BaseResponseMsg(true,appUrl);
        } else {
            return new BaseResponseMsg(false, "系统没有维护此导购员信息！");
        }
    }
}
