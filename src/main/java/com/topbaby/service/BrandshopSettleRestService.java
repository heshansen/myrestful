package com.topbaby.service;

import com.topbaby.ecommerce.brandshop.entity.*;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;

import java.util.Map;

/**
 * Created by xianghui
 */
public interface BrandshopSettleRestService {
    PageModel<BrandshopUserWithdraw> getCashes(Map map , PageQueryModel pageQueryModel) ;
    PageModel<BrandshopUserSettlement> getBalances(Map map, PageQueryModel pageQueryModel);
    PageModel<BrandshopUserOrderProfit> getBalanceOrders(Map params, PageQueryModel pageQueryModel);
    PageModel<BrandshopUsersMemberDevProfit> memberDevBalances(Long id , Map params, PageQueryModel pageQueryModel);
    PageModel<BrandshopUserRewardActivityProfit> rewardActivityBalances(Long id , Map params, PageQueryModel pageQueryModel);
}
