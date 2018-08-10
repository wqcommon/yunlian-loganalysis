package com.yunlian.loganalysis.dao;

import com.yunlian.loganalysis.po.StatCallDailyPo;

import java.util.List;
import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:56
 */
public interface StatCallDailyDao {

    //批量插入
    void insertBatch(List<StatCallDailyPo> list);

    //根据statDate更新
    int update(StatCallDailyPo po);

    //根据条件查询排除时间的记录
    List<StatCallDailyPo> queryExcludeTime(Map<String,Object> queryMap);
}
