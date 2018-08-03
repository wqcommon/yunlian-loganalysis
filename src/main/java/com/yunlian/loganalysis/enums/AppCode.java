package com.yunlian.loganalysis.enums;

/**
 * @author qiang.wen
 * @date 2018/7/31 17:11
 */
public enum AppCode {

    MIDDLE("200c0b42d77e44b79d1cea1cfbfdbc63","中间平台"),
    DEPOT("927f951e5e127ea1fb2582695ec7ddff","仓海帮应用"),
    SHIP("fed997dacf72fe4957c0c48631550d69","船运帮应用"),
    CAR("a3e8a8aa8c32e0fe29f3d8e8584f3a21","66快车应用");

    private String appCode;

    private String appName;

    private AppCode(String appCode,String appName){
        this.appCode = appCode;
        this.appName = appName;
    }

    public String getAppCode() {
        return appCode;
    }

    public String getAppName() {
        return appName;
    }
}
