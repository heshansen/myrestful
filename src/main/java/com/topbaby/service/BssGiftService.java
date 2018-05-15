package com.topbaby.service;

import com.topbaby.ecommerce.gift.vo.GiftVO;
import org.esbuilder.business.model.PageModel;

import java.util.Map;

/**
 * <p>Title:门店赠品管理服务 </p>
 *
 * @Author heshansen
 * @Date 2016-12-23 14:34.
 * @Version V1.0
 */
public interface BssGiftService {
    /**
     * 获取赠品列表信息
     */
    PageModel<GiftVO> getGiftPageList(Map<String, Object> param) throws Exception;
}
