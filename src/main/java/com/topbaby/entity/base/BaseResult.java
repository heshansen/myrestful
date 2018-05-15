package com.topbaby.entity.base;

/**
 * details： 基本返回结果实体
 * Created by sxy on 16-12-9.
 */
public class BaseResult extends BaseResponseMsg {

    /**
     * 商品总数量
     */
    private int totalNum;
    /**
     * 失败数量
     */
    private int failNum;
    /**
     * 成功数量
     */
    private int successNum;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getFailNum() {
        return failNum;
    }

    public void setFailNum(int failNum) {
        this.failNum = failNum;
    }

    public int getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(int successNum) {
        this.successNum = successNum;
    }
}
