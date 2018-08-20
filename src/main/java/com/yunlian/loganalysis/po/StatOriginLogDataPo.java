package com.yunlian.loganalysis.po;

import java.time.LocalDateTime;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:24
 *
 * 日志源数据Po
 */
public class StatOriginLogDataPo {

    //主键
    private int id;

    //全局id
    private String uid;

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
    private Integer requestLength;

    //记录从哪个页面链接访问过来的
    private String httpReferer;

    //目标服务地址
    private String upstreamAddr;

    //请求状态
    private Integer status;

    //整个请求时间
    private String requestTime;

    //服务端响应时间
    private String upstreamResponseTime;

    //发送给客户端的字节数，不包含响应头大小
    private Integer bodyBytesSent;

    //header中的token的值
    private String httpToken;

    //header中appCode的值
    private String httpAppCode;

    //请求客户端的信息
    private String httpUserAgent;

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

    public Integer getRequestLength() {
        return requestLength;
    }

    public void setRequestLength(Integer requestLength) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public Integer getBodyBytesSent() {
        return bodyBytesSent;
    }

    public void setBodyBytesSent(Integer bodyBytesSent) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
