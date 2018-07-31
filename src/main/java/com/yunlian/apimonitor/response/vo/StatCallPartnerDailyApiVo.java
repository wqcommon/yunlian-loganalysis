package com.yunlian.apimonitor.response.vo;

import java.io.Serializable;

/**
 * @author qiang.wen
 * @date 2018/7/31 15:13
 *
 * 按调用方每天每个api统计
 */
public class StatCallPartnerDailyApiVo extends StatCallDailyApiVo implements Serializable{
    private static final long serialVersionUID = -4202137705062849557L;

    private String appCode;//调用方appCode

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}
