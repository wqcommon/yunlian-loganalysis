package com.yunlian.loganalysis.dao;

import com.yunlian.loganalysis.po.TbTestPo;

import java.util.List;
import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/8 14:10
 */
public interface TbTestDao {

    List<TbTestPo> query(Map<String,Object> paramMap);

    int insert(TbTestPo po);
}
