package com.topbaby.common;

/**
 * details：常量类
 * Created by sxy on 16-12-6.
 */
public class TopConstants {

    /** 基本提示信息 */
    public static final String SUCCESSINFO = "success";
    public static final String FAILINFO = "fail";
    public static final String GETINFOFAIL = "获取数据失败，请重试！";
    public static final String EXCEPTIONINFO = "数据验证失败，请重试！";
    public static final String MANDATORYISNULL = "必填项为空！";
    public static final String PRODUCTIDISNULL = "商品ID为空！";
    public static final String BRANDIDISNULL = "商品ID为空！";
    public static final String IDISNULL = "ID为空！";
    public static final String PRODUCTSKUIDISNULL = "商品SKU ID为空！";
    public static final String DATAISNULL = "数据为空！";
    public static final String PRODUCTIMAGEISNULL = "商品主图为空！";
    public static final String DATAREPEAT = "商品货号重复！";
    public static final String SKUREPEAT = "商品SKU重复！";
    public static final String INTEGRALERROR = "获取退还积分失败！";

    /** 商品信息获取失败 */
    public static final String PRODUCTNOTEXSIT = "商品不存在，请检查商品ID是否正确！";
    public static final String USERACCOUNTNOTEXSIT = "导购员账户不存在！";
    /** 商品添加失败信息 */
    public static final String MERCHANTISINVALID = "商户不存在！";
    public static final String BRANDISINVALID = "品牌不存在！";
    public static final String CATAGORYISINVALID = "种类不存在！";
    public static final String STOCKNOISINVALID = "商品不存在,请确认商品货号是否正确！";
    public static final String PRODUCTIDISINVALID = "商品不存在！";
    public static final String SKUISINVALID = "商品SKU ID不存在！";
    public static final String ORDERISINVALID = "订单不存在！";
    public static final String ORDERITEMISINVALID = "该订单不存在任何商品！";
    public static final String ADDPRODUCTFAIL = "商品添加失败！";
    public static final String PRODUCTINFOINVALID = "商品信息不完善！";
    public static final String ADDPRODUCTSKUFAIL = "商品SKU添加失败！";

    /** 修改失败 */
    public static final String PUTPRODUCTFAIL = "商品修改失败！";
    public static final String PUTPRODUCTSKUFAIL = "商品修改失败！";

    /** 状态常量 */
    public static final String SKUATTRIBUTETYPE = "S";
    public static final String COMMONATTRIBUTETYPE = "C";
    public static final String COMMONATTRIBUTESTATUS = "E";//状态正常
    public static final String CANCELATTRIBUTETYPE = "C";//状态注销
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String NINE = "9";
    public static final String ZERO = "0";

    /** 常用属性名称 */
    public static final String SKUJSONVALUEATRRIVALUE = "value";
    public static final String SKULISTATRRINAME = "attrData";
    public static final String SKULISTPRODNAME = "prodSeq";
    public static final String SKUJSONVALUEATRRIID = "id";
    public static final String BRANDSHOPSEQ = "brandshopSeq";
    public static final String STATUS = "brandshopSeq";
    public static final String DELIVERYTYPE = "deliveryType";
    public static final String ORDERRULES = "orderDESC";
    public static final String EMPTYSTRING = "";

    /** 订单处理 */
    public static final String BRANDSHOPUSERINVALID = "导购员不属于该门店！";
    public static final String BRANDSHOPUSERLOGOUT = "导购员不存在或者已退出登录！";
    public static final String ORDERRECEIVEDERROR = "订单确认收货失败，请重试！";
    public static final String ORDERORUSERINVALID = "订单不存在或者导购员不存在！";
    public static final String ORDERISCANCELING = "该订单取消中，暂时无法收货！";
    public static final String ORDERISCANCELED = "该订单已取消，无法收货！";
    public static final String ORDERISDONE = "该订单处理完成，无法收货！";
    public static final String ORDERISUNHANDLE = "请选择状态已同意的退货订单!";
    public static final String ORDERRETURNERROR = "处理退货失败，请请检查并重试!";
    public static final String VERIFYERROR = "审核失败!";

    /** 门店导购员 */
    public static final String USERNOACCOUNT = "您尚未维护提现账户，请先维护提现账户!";
    public static final String USERMULTIWITHDRAW = "您已经提交过申请，处理完成后才能再次提交!";
    public static final String USERWITHDRAWINVALID = "您只能在25号以后才能提现操作!";
    public static final String USERWITHDRAWERROR = "提现失败，请重试!";
    public static final String USERBALANCEINVALID = "您的余额不足!";
    public static final String USERAMOUNTINVALID = "提现金额最少为10元，请重新输入!";

}
