package com.topbaby.service;

import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.entity.base.BaseResponseMsg;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.model.PageQueryModel;

import java.util.Map;

/**
 * Created by xh
 */
public interface BrandshopRestService {

    /**
     * 查看上架的商品
     */
    BaseResponseMsg getProductList(PageQueryModel queryModel, Map map) throws CoreException;

    /**
     * 更新库存包括批量更新
     */
    BaseResponseMsg updateRepCount(Long id, int stock, String updateNo) throws CoreException;

}
