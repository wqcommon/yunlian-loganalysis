package com.yunlian.loganalysis.po;

import java.time.LocalDateTime;

/**
 * @author qiang.wen
 * @date 2018/8/24 15:46
 */
public class StatSysApiPathPo {

    //主键id
    private int id;

    //path
    private String apiPath;

    //创建时间
    private LocalDateTime createTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
