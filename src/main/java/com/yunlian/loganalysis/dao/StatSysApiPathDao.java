package com.yunlian.loganalysis.dao;

import com.yunlian.loganalysis.po.StatSysApiPathPo;

import java.util.List;
import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/24 15:48
 */
public interface StatSysApiPathDao {

    //查询所有的记录
    List<StatSysApiPathPo> query(Map<String,Object> queryMap);
}
