package com.topbaby.entity;

import com.topbaby.ecommerce.order.entity.MemberOrderReturn;
import com.topbaby.entity.base.BaseResponseMsg;
import org.esbuilder.business.model.PageModel;

/**
 * <p>退货订单信息类</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-20
 */
public class OrderReturnInfo extends BaseResponseMsg {

    private PageModel<MemberOrderReturn> pageModel;

    public OrderReturnInfo() {
        super();
    }

    public OrderReturnInfo(PageModel<MemberOrderReturn> pageModel) {
        this.pageModel = pageModel;
    }

    public PageModel<MemberOrderReturn> getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel<MemberOrderReturn> pageModel) {
        this.pageModel = pageModel;
    }
}
