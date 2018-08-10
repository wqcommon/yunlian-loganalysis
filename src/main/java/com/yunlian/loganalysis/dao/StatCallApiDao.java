package com.yunlian.loganalysis.dao;

import com.yunlian.loganalysis.po.StatCallApiPo;

import java.util.List;
import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:56
 */
public interface StatCallApiDao {

    //批量插入
    void insertBatch(List<StatCallApiPo> list);

    //根据apiUrl更新记录
    int update(StatCallApiPo apiPo);

    //根据查询参数查询排除时间的记录
    List<StatCallApiPo> queryExcludeTime(Map<String,Object> queryMap);
}
