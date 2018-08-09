package com.yunlian.loganalysis.po;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:46
 *
 * 每日接口调用统计Po
 */
public class StatCallDailyPo {

    //全局唯一id
    private String uid;

    //统计日期
    private LocalDate statDate;

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

    public LocalDate getStatDate() {
        return statDate;
    }

    public void setStatDate(LocalDate statDate) {
        this.statDate = statDate;
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
