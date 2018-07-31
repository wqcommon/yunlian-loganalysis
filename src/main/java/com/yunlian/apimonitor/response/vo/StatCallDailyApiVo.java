package com.yunlian.apimonitor.response.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qiang.wen
 * @date 2018/7/31 15:05
 * 按每天每个api的维度统计
 */
public class StatCallDailyApiVo extends StatCallBaseVo implements Serializable{
    private static final long serialVersionUID = 1481071304108631285L;

    private String statDate;//统计日期

    private String apiUrl;//api路径

    private long minResponseTime;//最小响应时间

    private long maxResponseTime;//最大响应时间

    private long avgResponseTime;//平均响应时间

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public long getMinResponseTime() {
        return minResponseTime;
    }

    public void setMinResponseTime(long minResponseTime) {
        this.minResponseTime = minResponseTime;
    }

    public long getMaxResponseTime() {
        return maxResponseTime;
    }

    public void setMaxResponseTime(long maxResponseTime) {
        this.maxResponseTime = maxResponseTime;
    }

    public long getAvgResponseTime() {
        return avgResponseTime;
    }

    public void setAvgResponseTime(long avgResponseTime) {
        this.avgResponseTime = avgResponseTime;
    }
}
