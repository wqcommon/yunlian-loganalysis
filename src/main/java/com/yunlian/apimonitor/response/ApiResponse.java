package com.yunlian.apimonitor.response;

import com.yunlian.apimonitor.enums.ErrorMsg;

import java.io.Serializable;

/**
 * @author qiang.wen
 * @date 2018/7/31 10:16
 *
 * 返回结果
 */
public class ApiResponse<T> implements Serializable{

    private static final long serialVersionUID = -2342598956617529288L;

    private int code;

    private String msg;

    private T data;


    private ApiResponse(){

    }

    private ApiResponse(int code,String  msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static  <T>  ApiResponse<T> OK(T data){
        return new ApiResponse<>(ErrorMsg.OK.getCode(),ErrorMsg.OK.getMsg(),data);
    }

    public static <T> ApiResponse<T> ERROR(T data){
        return new ApiResponse<>(ErrorMsg.SYSTEM_ERROR.getCode(),ErrorMsg.SYSTEM_ERROR.getMsg(),data);
    }

    public static <T> ApiResponse<T> response(int code,String msg,T data){
        return new ApiResponse<>(code,msg,data);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
