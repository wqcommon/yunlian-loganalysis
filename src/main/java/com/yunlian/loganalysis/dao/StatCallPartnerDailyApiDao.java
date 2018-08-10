package com.yunlian.loganalysis.dao;

import com.yunlian.loganalysis.po.StatCallPartnerDailyApiPo;

import java.util.List;
import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:57
 */
public interface StatCallPartnerDailyApiDao {

    //批量插入
    void insertBatch(List<StatCallPartnerDailyApiPo> list);

    //更新
    int update(StatCallPartnerDailyApiPo po);

    //查询单条记录
    StatCallPartnerDailyApiPo getByAppStatDateAndUrl(Map<String,Object> queryMap);
}
