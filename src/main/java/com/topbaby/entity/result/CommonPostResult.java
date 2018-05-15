package com.topbaby.entity.result;

import com.topbaby.entity.base.BaseResult;

import java.util.List;

/**
 * details： 商品添加返回结果实体
 * Created by sxy on 16-12-7.
 */
public class CommonPostResult<T> extends BaseResult {
    /**
     * 添加失败商品列表
     */
    private List<T> failInfoList;

    public CommonPostResult(int totalNum, int failNum, int successNum, List<T> failInfoList) {
        super();
        this.setTotalNum(totalNum);
        this.setFailNum(failNum);
        this.setSuccessNum(successNum);
        this.failInfoList = failInfoList;
    }

    public CommonPostResult() {
    }

    public List<T> getFailInfoList() {
        return failInfoList;
    }

    public void setFailInfoList(List<T> failInfoList) {
        this.failInfoList = failInfoList;
    }
}
