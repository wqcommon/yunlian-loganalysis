package com.yunlian.apimonitor.response.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qiang.wen
 * @date 2018/7/31 14:50
 *
 * 每日接口调用统计
 */
public class StatCallDailyVo extends StatCallBaseVo implements Serializable{


    private static final long serialVersionUID = -5607340338392022692L;

    private String statDate;//统计日期


    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }
}
