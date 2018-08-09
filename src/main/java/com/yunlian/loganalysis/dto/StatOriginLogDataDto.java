package com.yunlian.loganalysis.dto;

import java.io.Serializable;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:24
 *
 * 日志源数据DTO
 */
public class StatOriginLogDataDto implements Serializable{

    private static final long serialVersionUID = 539155325996146094L;

    //访问网站的客户端地址ip
    private String remoteAddr;

    //客户端名称
    private String remoteUser;

    //通用日志格式下的本地时间
    private String timeLocal;

    //请求地址(IP或域名)
    private String httpHost;

    //请求的url和http协议(GET,POST,DEL等)
    private String request;

    //请求的长度
    private String requestLength;

    //记录从哪个页面链接访问过来的
    private String httpReferer;

    //目标服务地址
    private String upstreamAddr;

    //请求状态
    private String status;

    //整个请求时间
    private String requestTime;

    //服务端响应时间
    private String upstreamResponseTime;

    //发送给客户端的字节数，不包含响应头大小
    private String bodyBytesSent;

    //header中的token的值
    private String httpToken;

    //header中appCode的值
    private String httpAppCode;

    //请求客户端的信息
    private String httpUserAgent;

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public String getTimeLocal() {
        return timeLocal;
    }

    public void setTimeLocal(String timeLocal) {
        this.timeLocal = timeLocal;
    }

    public String getHttpHost() {
        return httpHost;
    }

    public void setHttpHost(String httpHost) {
        this.httpHost = httpHost;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequestLength() {
        return requestLength;
    }

    public void setRequestLength(String requestLength) {
        this.requestLength = requestLength;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
    }

    public String getUpstreamAddr() {
        return upstreamAddr;
    }

    public void setUpstreamAddr(String upstreamAddr) {
        this.upstreamAddr = upstreamAddr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getUpstreamResponseTime() {
        return upstreamResponseTime;
    }

    public void setUpstreamResponseTime(String upstreamResponseTime) {
        this.upstreamResponseTime = upstreamResponseTime;
    }

    public String getBodyBytesSent() {
        return bodyBytesSent;
    }

    public void setBodyBytesSent(String bodyBytesSent) {
        this.bodyBytesSent = bodyBytesSent;
    }

    public String getHttpToken() {
        return httpToken;
    }

    public void setHttpToken(String httpToken) {
        this.httpToken = httpToken;
    }

    public String getHttpAppCode() {
        return httpAppCode;
    }

    public void setHttpAppCode(String httpAppCode) {
        this.httpAppCode = httpAppCode;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    public void setHttpUserAgent(String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
    }
}
