package com.topbaby.service.impl;

import com.topbaby.ecommerce.brandshop.entity.*;
import com.topbaby.ecommerce.brandshop.service.*;
import com.topbaby.ecommerce.core.Constants;
import com.topbaby.ecommerce.core.entity.Member;
import com.topbaby.ecommerce.core.service.MemberService;
import com.topbaby.ecommerce.order.entity.Order;
import com.topbaby.ecommerce.order.entity.OrderStatus;
import com.topbaby.ecommerce.order.service.OrderService;
import com.topbaby.entity.BrandshopUserUnsettlesInfo;
import com.topbaby.service.BrandshopUnsettleRestService;
import org.apache.commons.collections.CollectionUtils;
import org.esbuilder.base.CoreException;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianghui
 */
@Service
@Transactional

public class BrandshopUnsettleRestServiceImpl implements BrandshopUnsettleRestService {

    @Autowired
    BrandshopUserOrderProfitService brandshopUserOrderProfitService;
    @Autowired
    OrderService orderService;
    @Autowired
    BrandshopUserMemberService brandshopUserMemberService;
    @Autowired
    BrandshopUserMemberDevProfitService brandshopUserMemberDevProfitService;

    @Autowired
    BrandshopUserRewardActivityProfitService brandshopUserRewardActivityProfitService;
    @Autowired
    BrandshopUserRewardActivityService brandshopUserRewardActivityService;
    @Autowired
    BrandshopUserOrderActivityRelationService brandshopUserOrderActivityRelationService;
    @Autowired
    MemberRecommendService memberRecommendService;
    @Autowired
    BrandshopService brandshopService;
    @Autowired
    MemberService memberService;

    /**
     * 查询导购员未结算list
     *
     * @param id             未导购员id
     * @param pageQueryModel
     * @return
     * @throws CoreException
     */
    public BrandshopUserUnsettlesInfo getUnliquidatedList(Long id, PageQueryModel pageQueryModel) throws CoreException {
        Map<String, Object> param = new HashMap<String, Object>();
        BrandshopUserUnsettlesInfo brandshopUserUnsettlesInfo = new BrandshopUserUnsettlesInfo();
        param.put("userSeq", id);
        /** 获取订单分润表的数据*/
        List<BrandshopUserOrderProfit> orderProfits = brandshopUserOrderProfitService.getList(param);
        /** 获取订单分润表的orderSeq List数据*/
        List<Long> orderSeqLs = new ArrayList<Long>();
        if (CollectionUtils.isNotEmpty(orderProfits)) {
            for (BrandshopUserOrderProfit brandshopUserOrderProfit : orderProfits) {
                orderSeqLs.add(brandshopUserOrderProfit.getOrderSeq());
            }
        }
        param.clear();

        param.put("consigner", id);
        param.put("status", OrderStatus.RECEIVED.getCode());
        param.put(Constants.ORDERRULES, Constants.ORDERRULES);
        if (CollectionUtils.isNotEmpty(orderSeqLs)) {
            param.put("orderSeqLs", orderSeqLs);
        }
        /**获取该导购员已处理并且不在分润表中的订单数据（未结算的数据）*/
        PageModel<Order> list = orderService.getPageList(param, pageQueryModel);
        brandshopUserUnsettlesInfo.setOrderPageModel(list);

        /** 导购员处理订单奖励设置*/
        MemberRecommend memberRecommend = memberRecommendService.getMemberRecommend(id);
        brandshopUserUnsettlesInfo.setMemberRecommend(memberRecommend);

        return brandshopUserUnsettlesInfo;
    }

    /**
     * 导购员会员发展未结算list
     *
     * @param id 为导购员id
     * @throws CoreException
     */
    public BrandshopUserUnsettlesInfo memberdevUnliquidatedList(Long id, Map params, PageQueryModel pageQueryModel) throws CoreException {
        PageModel<BrandshopUserMember> brandshopUserMemberList = brandshopUserMemberService
                .getPageList(params, pageQueryModel);
        BrandshopUserUnsettlesInfo brandshopUserUnsettlesInfo = new BrandshopUserUnsettlesInfo();
        brandshopUserUnsettlesInfo.setBrandshopUserMemberPageModel(brandshopUserMemberList);
        /**导购员处理订单奖励设置*/
        MemberRecommend memberRecommend = memberRecommendService.getMemberRecommend(id);
        brandshopUserUnsettlesInfo.setMemberRecommend(memberRecommend);
        return brandshopUserUnsettlesInfo;
    }

    /**
     * 导购员奖励活动会员发展未结算list
     */
    public PageModel<Member> rewActMemDevUnliquidated(BrandshopUser brandshopUser, PageQueryModel pageQueryModel) {
        Map<String, Object> param = new HashMap<String, Object>();
        Brandshop brandshop = brandshopService.getModel(brandshopUser.getBrandshopSeq());
        BrandshopUserRewardActivity bsura = brandshopUserRewardActivityService.getBrandshopUserRewardActivity(brandshop, null, "0");

        PageModel<Member> list = new PageModel<Member>();

        if (bsura != null) {

            param.put("rewardWay", "0");// 会员发展
            param.put("userSeq", brandshopUser.getId());
            List<BrandshopUserRewardActivityProfit> bsUserRewardActivityProfits = brandshopUserRewardActivityProfitService
                    .getList(param);
            param.clear();
            param.put("userSeq", brandshopUser.getId());
            param.put("startTime", bsura.getStartDate());
            param.put("endDate", bsura.getEndDate());
            if (CollectionUtils.isNotEmpty(bsUserRewardActivityProfits)
                    && bsUserRewardActivityProfits.size() > 0) {
                param.put("bsUserRewardActivityProfits", bsUserRewardActivityProfits);
            }
            list = memberService.selectMembersForBrandshopUser(pageQueryModel, param);
        }
        return list;
    }

    /**
     * 导购员奖励活动订单未结算list
     *
     * @param id 为导购员id
     * @return
     */
    public PageModel<BrandshopUserOrderActivityRelation> rewActOrderdealUnliqudated(Long id, PageQueryModel
            pageQueryModel) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("rewardWay", "1");// 处理订单
        param.put("userSeq", id);
        List<BrandshopUserRewardActivityProfit> bsUserRewardActivityProfits = brandshopUserRewardActivityProfitService
                .getList(param);

        param.clear();
        param.put("userSeq", id);
        /** rewardType为1时处理订单*/
        param.put("rewardType", "1");
        if (CollectionUtils.isNotEmpty(bsUserRewardActivityProfits)
                && bsUserRewardActivityProfits.size() > 0) {
            param.put("bsUserRewardActivityProfits", bsUserRewardActivityProfits);
        }
        PageModel<BrandshopUserOrderActivityRelation> list = brandshopUserOrderActivityRelationService
                .getPageList(param, pageQueryModel);

        return list;
    }

}
