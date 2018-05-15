package com.topbaby.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topbaby.ecommerce.brandshop.entity.Brandshop;
import com.topbaby.ecommerce.brandshop.entity.BrandshopUser;
import com.topbaby.ecommerce.brandshop.service.BrandshopService;
import com.topbaby.ecommerce.brandshop.service.BrandshopUserService;
import com.topbaby.ecommerce.core.Constants;
import com.topbaby.ecommerce.verify.entity.Verify;
import com.topbaby.ecommerce.verify.service.VerifyService;
import com.topbaby.entity.base.BaseResponseMsg;
import com.topbaby.service.BssUserAccountSetService;
import org.apache.commons.collections.CollectionUtils;
import org.esbuilder.business.model.PageModel;
import org.esbuilder.business.model.PageQueryModel;
import org.esbuilder.common.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: 导购员账户维护</p>
 *
 * @Author heshansen
 * @Date 2016-12-23 09:58.
 * @Version V1.0
 */
@Service
public class BssUserAccountSetServiceImpl implements BssUserAccountSetService {

    @Autowired
    private VerifyService verifyService;
    @Autowired
    private BrandshopUserService brandshopUserService;

    @Autowired
    private BrandshopService brandshopService;

    /**
     * 获得导购员账户基本信息
     * @param userId　用户id
     * @return
     * @throws Exception
     */
    public BrandshopUser getDetail(Long userId) throws Exception {
        BrandshopUser bsUser = brandshopUserService.getModel(userId);
        // 银行卡号只显示前2位和后4位，中间的用*号代替
        if (bsUser != null) {
            String accountNumber = bsUser.getCardNumber();
            if (!StringUtils.isNullOrEmpty(accountNumber)) {
                String bankAccount = accountNumber.substring(0, 2) + getStar(accountNumber,6) + accountNumber.substring(accountNumber.length() - 4);
                bsUser.setCardNumber(bankAccount);
            }
        }

        return bsUser;
    }

    /**
     * 显示星号个数
     *
     * @param cardNumber 银行卡号
     * @return {cardNumber.length()-limit}个星号
     */
    public String getStar(String cardNumber,int limit){
        String star="";
        for (int i=0;i<cardNumber.length()-limit;i++){
            star+="*";
        }
        return star;
    }

    /**
     * 更新导购员账户信息
     * @param param 请求参数
     * @return
     * @throws Exception
     */
    public BaseResponseMsg postUpdate(Map<String, Object> param) throws Exception {
        BrandshopUser bsUser = (BrandshopUser) param.get("bsUser");
        String idCardFrontImg = (String) param.get("idCardFrontImg");
        String idCardBackImg = (String) param.get("idCardBackImg");

        /*导购员账户信息修改需要平台审核*/
        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put("verifyType", Constants.VERIFY_TYPE_BRANDSHOPUSER_ACCOUNT);
        param1.put("merchantVerifyProcessing", "merchantVerifyProcessing");
        param1.put("submitUserSeq", bsUser.getId());
        List<Verify> list = verifyService.getList(param1);
        if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
            return new BaseResponseMsg(false, "审核队列存在未审核记录，请平台审核之后再提交数据！");
        }

        BrandshopUser target = brandshopUserService.getModel(bsUser.getId());
        // 如果不修改银行帐号，则用原来的；如果修改了，就用新的
        if (!StringUtils.isNullOrEmpty(bsUser.getCardNumber()) && bsUser.getCardNumber().length() == 19) {
            if (bsUser.getCardNumber().contains("*")) {
                bsUser.setCardNumber(target.getCardNumber());
            }
        } else {
            return new BaseResponseMsg(false, "银行卡号输入异常！可能为空或没有19位！");
        }

        if (!StringUtils.isNullOrEmpty(idCardFrontImg)) {
            target.setIdCardFrontImage(Long.parseLong(idCardFrontImg));
        }
        if (!StringUtils.isNullOrEmpty(idCardBackImg)) {
            target.setIdCardBackImage(Long.parseLong(idCardBackImg));
        }
        BeanUtils.copyProperties(bsUser, target);

        Brandshop brandshop = brandshopService.getModel(target.getBrandshopSeq());

        Verify verify = new Verify();
        verify.setEntityProp1(target.getSalesName());
        verify.setEntityProp2(brandshop.getName());
        verify.setEntityProp3(target.getDepositBank());
        verify.setEntityProp4(target.getCellphone());
        verify.setSubmitUserSeq(target.getId());
        verify.setSubmitOrgSeq(target.getBrandshopSeq());
        verify.setUpdateSeq(target.getId());
        verify.setCreateDate(new Date());
        verify.setStatus(Constants.VERIFY_STATUS_NEW);
        verify.setVerifyType(Constants.VERIFY_TYPE_BRANDSHOPUSER_ACCOUNT);
        verify.setOperation(Constants.VERIFY_OPERATION_UPDATE);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr;
        try {
            jsonStr = objectMapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new BaseResponseMsg(false, "导购员账户信息解析异常！");
        }
        verify.setEntity(jsonStr);

        verifyService.add(verify);

        return new BaseResponseMsg();
    }

    /**
     * 导购员账户审核列表
     * @param param 请求参数
     * @return
     * @throws Exception
     */
    public PageModel<Verify> verifyList(Map<String, Object> param) throws Exception {
        PageQueryModel pageQueryModel= (PageQueryModel) param.get("pageQueryModel");

        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put("status", param.get("status"));
        param1.put("submitUserSeq", param.get("userId"));
        param1.put("verifyType", Constants.VERIFY_TYPE_BRANDSHOPUSER_ACCOUNT);
        param1.put(Constants.ORDERRULES, Constants.ORDERRULES);
        PageModel<Verify> pageModel= verifyService.getPageList(param1, pageQueryModel);

        return pageModel;
    }
}
