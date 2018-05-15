package com.topbaby.service.impl;

import com.topbaby.common.TopConstants;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUserAccount;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUserAccountHistory;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUserWithdraw;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserAccountHistoryService;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserAccountService;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserService;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserWithdrawService;
import com.topbaby.entity.UserAccountHistoryPageParam;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BrandshopUserAccountRestService;
import org.apache.commons.lang.StringUtils;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>导购员账户服务实现</p>
 * details：
 * 导购员提现、导购员账户查看、导购员资金明细查看
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-20
 */
@Service
public class BrandshopUserAccountRestServiceImpl implements BrandshopUserAccountRestService {

    @Autowired
    private BrandshopUserAccountService brandshopUserAccountService;
    @Autowired
    private BrandshopUserService brandshopUserService;
    @Autowired
    private BrandshopUserWithdrawService brandshopUserWithdrawService;
    @Autowired
    private BrandshopUserAccountHistoryService brandshopUserAccountHistoryService;

    /**
     * 获取导购员账户信息
     *
     * @param brandshopUserID 导购员ID
     * @return BaseResponseMsg
     */
    public Object getUserAccount(Long brandshopUserID) throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", brandshopUserID);
        BrandshopUserAccount brandshopUserAccount;
        brandshopUserAccount = brandshopUserAccountService.getModel(params);

        if (brandshopUserAccount == null) {
            return new BaseResponseMsg(false, TopConstants.USERACCOUNTNOTEXSIT);
        }

        return brandshopUserAccount;
    }

    /**
     * 导购员提现申请
     *
     * @param amount 提现金额
     * @return BaseResponseMsg
     */
    public BaseResponseMsg withdrawCash(BigDecimal amount, Long brandshopUserID) throws Exception {

        BrandshopUser brandshopUser = brandshopUserService.getModel(brandshopUserID);
        if (StringUtils.isEmpty(brandshopUser.getCardNumber()) && StringUtils.isEmpty(brandshopUser.getWechatNumber())) {
            return new BaseResponseMsg(false, TopConstants.USERNOACCOUNT);
        } else {
            /** 判断是否是在每个月的25号以后 */
            Calendar now = Calendar.getInstance();
            int day = now.get(Calendar.DAY_OF_MONTH);
            if (day < 25) {
                return new BaseResponseMsg(false, TopConstants.USERWITHDRAWINVALID);
            } else {
                //判断余额是否足够
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("userId", brandshopUser.getId());
                BrandshopUserAccount brandshopUserAccounts = brandshopUserAccountService.getModel(param);
                if (brandshopUserAccounts.getBalance().compareTo(amount) < 0) {
                    return new BaseResponseMsg(false, TopConstants.USERBALANCEINVALID);
                } else {
                    /** 判断提现金额是否小于10元 */
                    BigDecimal minPrice = new BigDecimal(10);
                    if (amount.compareTo(minPrice) < 0) {
                        return new BaseResponseMsg(false, TopConstants.USERAMOUNTINVALID);
                    } else {
                        /** 判断是否已经存在“处理中的申请” */
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("applyUser", brandshopUser.getId());
                        params.put("status", TopConstants.NINE);
                        List<BrandshopUserWithdraw> brandshopUserWithdrawList = brandshopUserWithdrawService.getList(params);
                        if (brandshopUserWithdrawList != null && brandshopUserWithdrawList.size() > 0) {
                            return new BaseResponseMsg(false, TopConstants.USERMULTIWITHDRAW);
                        } else {
                            // 在导购员提现明细添加一条记录
                            BrandshopUserWithdraw brandshopUserWithdraw = new BrandshopUserWithdraw();
                            brandshopUserWithdraw.setAmount(amount);
                            brandshopUserWithdraw.setApplyUser(brandshopUser.getId());
                            brandshopUserWithdraw.setStatus(TopConstants.NINE.charAt(0));
                            brandshopUserWithdrawService.add(brandshopUserWithdraw);
                            return new BaseResponseMsg();
                        }
                    }
                }
            }
        }
    }

    /**
     * 导购员提现明细
     *
     * @param pageParam 导购员资金明细查询参数
     * @return BaseResponseMsg
     */
    public Object getAccountHistory(UserAccountHistoryPageParam pageParam, Long brandshopUserID) throws Exception {

        /** 分页对象初始化　*/
        PageQueryModel pageQueryModel = new PageQueryModel();
        pageQueryModel.setPage(pageParam.getPage());
        pageQueryModel.setStart(pageParam.getStart());
        pageQueryModel.setLimit(pageParam.getLimit());

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", brandshopUserID);
        params.put("startTime", pageParam.getStartTime());
        params.put("endTime", pageParam.getEndTime());

        PageModel<BrandshopUserAccountHistory> pageModel = brandshopUserAccountHistoryService.getPageList(params, pageQueryModel);

        return pageModel;
    }
}
