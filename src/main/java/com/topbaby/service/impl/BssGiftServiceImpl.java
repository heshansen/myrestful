package com.topbaby.service.impl;

import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserService;
import com.topbaby.ecommerce.gift.service.MemberGiftAccountService;
import com.topbaby.ecommerce.gift.vo.GiftVO;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssGiftService;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.esbuilder.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title:门店赠品管理实例 </p>
 *
 * @Author heshansen
 * @Date 2016-12-23 14:36.
 * @Version V1.0
 */
@Service
public class BssGiftServiceImpl implements BssGiftService {

    @Autowired
    private BrandshopUserService brandshopUserService;
    @Autowired
    private MemberGiftAccountService memberGiftAccountService;

    /**
     * 获取赠品列表（分页）
     * @param param 请求参数
     * @return
     * @throws Exception
     */
    public PageModel<GiftVO> getGiftPageList(Map<String, Object> param) throws Exception {
        Long userId = (Long) param.get("userId");
        String status = (String) param.get("status");
        PageQueryModel pageQueryModel = (PageQueryModel) param.get("pageQueryModel");

        PageModel<GiftVO> pageModel = new PageModel<GiftVO>();

        BrandshopUser brandshopUser = brandshopUserService.getModel(userId);
        if (brandshopUser != null) {

            Map<String, Object> param1 = new HashMap<String, Object>();
            if (!StringUtils.isNullOrEmpty(status)) {
                param1.put("brandshopSeq", brandshopUser.getBrandshopSeq());
                param1.put("status", status);

                //获取gift的列表
                if ("0".equals(status)) {
                    param1.put("beginDate", param.get("createDate"));
                    pageModel = memberGiftAccountService.getMemberGiftVOList(param1, pageQueryModel);
                } else if ("1".equals(status)) {
                    param1.put("stopDate", param.get("endDate"));
                    pageModel = memberGiftAccountService.getMemberGiftVOList(param1, pageQueryModel);
                }
            }
        }

        return pageModel;
    }
}
