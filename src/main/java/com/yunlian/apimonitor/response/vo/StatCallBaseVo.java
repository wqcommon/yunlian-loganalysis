package com.yunlian.apimonitor.response.vo;

import java.io.Serializable;

/**
 * @author qiang.wen
 * @date 2018/7/31 15:03
 *
 * 调用统计基础类
 */
public class StatCallBaseVo implements Serializable{

    private static final long serialVersionUID = -7530895329240828820L;

    private long totalCallNum;//总调用次数

    private long failureCallNum;//错误调用次数

    private long successCallNum;//成功调用次数

    public long getTotalCallNum() {
        return totalCallNum;
    }

    public void setTotalCallNum(long totalCallNum) {
        this.totalCallNum = totalCallNum;
    }

    public long getFailureCallNum() {
        return failureCallNum;
    }

    public void setFailureCallNum(long failureCallNum) {
        this.failureCallNum = failureCallNum;
    }

    public long getSuccessCallNum() {
        return successCallNum;
    }

    public void setSuccessCallNum(long successCallNum) {
        this.successCallNum = successCallNum;
    }
}
