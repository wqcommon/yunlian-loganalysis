package com.yunlian.loganalysis.dao;

import com.yunlian.loganalysis.po.StatCallDailyApiPo;

import java.util.List;
import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:57
 */
public interface StatCallDailyApiDao {

    //批量插入
    void insertBatch(List<StatCallDailyApiPo> list);

    //更新
    int update(StatCallDailyApiPo po);

    //更加statDate和apiUrl查询结果
    StatCallDailyApiPo getByStatDateAndUrl(Map<String,Object> queryMap);
}
