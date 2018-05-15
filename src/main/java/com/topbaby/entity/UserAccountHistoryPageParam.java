package com.topbaby.entity;

/**
 * <p>导购员资金明细查询参数对象</p>
 *
 * @author SunXiaoYuan
 * @version 1.0
 * @since 16-12-20
 */
public class UserAccountHistoryPageParam {

    /**
     * 页数
     */
    private int page;
    /**
     * 每页展示数量
     */
    private int limit;
    /**
     * 起始位置
     */
    private int start;

    /**
     * 开始时间 格式（2016-01-23 19:06:00 或者 2016-01-23）
     */
    private String startTime;
    /**
     * 结束时间 格式（2016-01-23 19:06:00 或者 2016-01-23）
     */
    private String endTime;

    public UserAccountHistoryPageParam() {
    }

    public UserAccountHistoryPageParam(int page, int limit, int start, String startTime, String endTime) {
        this.page = page;
        this.limit = limit;
        this.start = start;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
