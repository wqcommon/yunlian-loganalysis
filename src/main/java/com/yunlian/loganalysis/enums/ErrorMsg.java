package com.yunlian.loganalysis.enums;

/**
 * @author qiang.wen
 * @date 2018/7/31 10:13
 *
 * 错误信息
 */
public enum ErrorMsg {

    OK(0,"成功"),
    SYSTEM_ERROR(50000,"系统错误");

    private int code;

    private String msg;

    private ErrorMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
