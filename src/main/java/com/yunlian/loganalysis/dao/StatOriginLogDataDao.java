package com.yunlian.loganalysis.dao;

import com.yunlian.loganalysis.po.StatOriginLogDataPo;

import java.util.List;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:55
 */
public interface StatOriginLogDataDao {

    /**
     * 批量插入
     * @param list
     */
    void insertBatch(List<StatOriginLogDataPo> list);
}
