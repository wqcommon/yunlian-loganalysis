package com.yunlian.loganalysis.po;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:48
 * 每个调用方每天每个api的总调用统计Po
 */
public class StatCallPartnerDailyApiPo {

    //主键
    private int id;

    //全局唯一id
    private String uid;

    //调用方appCode
    private String appCode;

    //统计日期
    private LocalDate statDate;

    //调用api的全路径
    private String apiUrl;

    //总调用次数
    private long totalCallnum;

    //成功调用次数
    private long successCallnum;

    //失败调用次数
    private long failureCallnum;

    //最小响应时间(ms)
    private long minResponseTime;

    //最大响应时间(ms)
    private long maxResponseTime;

    //创建时间
    private LocalDateTime createTime;

    //更新时间
    private LocalDateTime updateTime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDate getStatDate() {
        return statDate;
    }

    public void setStatDate(LocalDate statDate) {
        this.statDate = statDate;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public long getTotalCallnum() {
        return totalCallnum;
    }

    public void setTotalCallnum(long totalCallnum) {
        this.totalCallnum = totalCallnum;
    }

    public long getSuccessCallnum() {
        return successCallnum;
    }

    public void setSuccessCallnum(long successCallnum) {
        this.successCallnum = successCallnum;
    }

    public long getFailureCallnum() {
        return failureCallnum;
    }

    public void setFailureCallnum(long failureCallnum) {
        this.failureCallnum = failureCallnum;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
