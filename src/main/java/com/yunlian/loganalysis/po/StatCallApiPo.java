package com.yunlian.loganalysis.po;

import java.time.LocalDateTime;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:42
 * 每个api的总调用统计 po
 */
public class StatCallApiPo {

    //全局唯一id
    private String uid;

    //调用api的全路径
    private String apiUrl;

    //总调用次数
    private long totalCallnum;

    //成功调用次数
    private long successCallnum;

    //失败调用次数
    private long failureCallnum;

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

    public void setFailureCallnum(long failureCallnum) {
        this.failureCallnum = failureCallnum;
    }

    public long getFailureCallnum() {
        return failureCallnum;
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
}