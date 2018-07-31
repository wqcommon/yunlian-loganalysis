package com.yunlian.apimonitor.response.vo;

import java.io.Serializable;

/**
 * @author qiang.wen
 * @date 2018/7/31 14:56
 *
 * 按api维度统计
 */
public class StatCallApiVo extends StatCallBaseVo implements Serializable{
    private static final long serialVersionUID = -73019770092682525L;

    private String apiUrl; //api全路径


    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
