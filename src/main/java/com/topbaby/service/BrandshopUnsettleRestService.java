package com.topbaby.service;

import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUserOrderActivityRelation;
import com.topbaby.ecommerce.core.entity.Member;
import com.topbaby.entity.BrandshopUserUnsettlesInfo;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;

import java.util.Map;

/**
 * Created by xianghui
 */
public interface BrandshopUnsettleRestService {
    BrandshopUserUnsettlesInfo getUnliquidatedList(Long id, PageQueryModel pageQueryModel) throws CoreException;

    BrandshopUserUnsettlesInfo memberdevUnliquidatedList(Long id , Map map, PageQueryModel pageQueryModel) throws CoreException;
    PageModel<Member> rewActMemDevUnliquidated(BrandshopUser brandshopUser, PageQueryModel pageQueryModel);

    PageModel<BrandshopUserOrderActivityRelation> rewActOrderdealUnliqudated(Long id, PageQueryModel pageQueryModel);


}
