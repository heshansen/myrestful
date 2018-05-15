package com.topbaby.entity;

import com.topbaby.ecommerce.brandshop.entity.BrandshopUserMember;
import com.topbaby.ecommerce.brandshop.entity.MemberRecommend;
import com.topbaby.ecommerce.order.entity.Order;
import com.topbaby.entity.base.BaseResponseMsg;
import org.esbuilder.business.model.PageModel;

/**
 * Created by xianghui on 16-12-16.
 * details： 导购员未结算信息
 */
public class BrandshopUserUnsettlesInfo extends BaseResponseMsg{
    private MemberRecommend memberRecommend ;
    private PageModel<Order> orderPageModel ;
    private PageModel<BrandshopUserMember> brandshopUserMemberPageModel ;

    public MemberRecommend getMemberRecommend() {
        return memberRecommend;
    }

    public void setMemberRecommend(MemberRecommend memberRecommend) {
        this.memberRecommend = memberRecommend;
    }


    public PageModel<Order> getOrderPageModel() {
        return orderPageModel;
    }

    public void setOrderPageModel(PageModel<Order> orderPageModel) {
        this.orderPageModel = orderPageModel;
    }

    public PageModel<BrandshopUserMember> getBrandshopUserMemberPageModel() {
        return brandshopUserMemberPageModel;
    }

    public void setBrandshopUserMemberPageModel(PageModel<BrandshopUserMember> brandshopUserMemberPageModel) {
        this.brandshopUserMemberPageModel = brandshopUserMemberPageModel;
    }
}
