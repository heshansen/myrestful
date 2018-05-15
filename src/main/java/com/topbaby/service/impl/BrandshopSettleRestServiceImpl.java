package com.topbaby.service.impl;

import com.topbaby.ecommerce.brandshop.entity.*;
import com.topbaby.ecommerce.brandshop.service.*;
import com.topbaby.service.BrandshopSettleRestService;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by xianghui
 */
@Service
@Transactional
public class BrandshopSettleRestServiceImpl implements BrandshopSettleRestService{
    @Autowired
    BrandshopUserMemberDevProfitService brandshopUserMemberDevProfitService;
    @Autowired
    BrandshopUserOrderProfitService brandshopUserOrderProfitService ;
    @Autowired
    BrandshopUserWithdrawService brandshopUserWithdrawService;
    @Autowired
    BrandshopUserSettlementService brandshopUserSettlementService ;
    @Autowired
    BrandshopUserRewardActivityProfitService brandshopUserRewardActivityProfitService;

    /** 提现明细*/
    public PageModel<BrandshopUserWithdraw> getCashes(Map params, PageQueryModel pageQueryModel) {
        PageModel<BrandshopUserWithdraw> list = brandshopUserWithdrawService.getPageList(params, pageQueryModel);

        return list;
    }


    /** 结算明细list*/
    public PageModel<BrandshopUserSettlement> getBalances(Map params, PageQueryModel pageQueryModel) {
        PageModel<BrandshopUserSettlement> brandshopUserSettlementList = brandshopUserSettlementService.getPageList(params, pageQueryModel);
        return brandshopUserSettlementList;
    }

    public PageModel<BrandshopUserOrderProfit> getBalanceOrders(Map params, PageQueryModel pageQueryModel) {
        PageModel<BrandshopUserOrderProfit> brandshopUserOrderProfitList =
                brandshopUserOrderProfitService.getPageList(params,
                        pageQueryModel);
        return brandshopUserOrderProfitList;
    }

    public PageModel<BrandshopUsersMemberDevProfit> memberDevBalances(Long id , Map params, PageQueryModel pageQueryModel) {
        BrandshopUserSettlement brandshopUserSettlement = brandshopUserSettlementService
                .getModel(id);

        PageModel<BrandshopUsersMemberDevProfit> brandshopUsersMemberDevProfitList = null;
        params.put("settlementDate", brandshopUserSettlement.getSettlementDate());
        brandshopUsersMemberDevProfitList = brandshopUserMemberDevProfitService.getPageList(
                params, pageQueryModel);
        return brandshopUsersMemberDevProfitList;
    }

    public PageModel<BrandshopUserRewardActivityProfit> rewardActivityBalances(Long id , Map params, PageQueryModel pageQueryModel) {
        params.put("settlementDate", brandshopUserSettlementService.getModel(id)
                .getSettlementDate());

        PageModel<BrandshopUserRewardActivityProfit> bsurcList = brandshopUserRewardActivityProfitService
                .getPageList(params, pageQueryModel);
        return bsurcList;
    }

}
