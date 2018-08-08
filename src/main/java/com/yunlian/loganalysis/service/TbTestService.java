package com.yunlian.loganalysis.service;

import com.alibaba.fastjson.JSONObject;
import com.yunlian.loganalysis.config.DbConfig;
import com.yunlian.loganalysis.dao.TbDemoDao;
import com.yunlian.loganalysis.dao.TbTestDao;
import com.yunlian.loganalysis.po.TbDemoPo;
import com.yunlian.loganalysis.po.TbTestPo;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author qiang.wen
 * @date 2018/8/8 14:21
 */
public class TbTestService {

    private static TbTestService tbTestService = new TbTestService();

    private TbTestService(){}

    public static TbTestService getInstance(){
        return tbTestService;
    }

    public List<TbTestPo> query(String name, BigDecimal money){
        Map<String,Object> queryMap = new HashMap<>(2);
        queryMap.put("name",name);
        queryMap.put("money",money);
        SqlSession session = DbConfig.openSqlSession(true);
        TbTestDao tbTestDao = session.getMapper(TbTestDao.class);
        List<TbTestPo> list = tbTestDao.query(queryMap);
        System.out.println("ret=======" + JSONObject.toJSONString(list));
        DbConfig.closeSession(session);
        return list;
    }


    public void insertData(){
        Random random = new Random();
        TbTestPo po = new TbTestPo();
        po.setMoney(new BigDecimal(random.nextInt(50)));
        po.setName("name_" + random.nextInt(100));

        TbDemoPo tbDemoPo = new TbDemoPo();
        tbDemoPo.setAddress("address_" + random.nextInt(100));
        tbDemoPo.setPhone("phone_" + random.nextInt(10000));
        SqlSession session = DbConfig.openSqlSession(false);
        try {
            TbDemoDao demoDao = session.getMapper(TbDemoDao.class);
            TbTestDao tbTestDao = session.getMapper(TbTestDao.class);
            demoDao.insert(tbDemoPo);
            tbTestDao.insert(po);
            String s = null;
            s.length();
            session.commit();
        }catch (Exception e){
            session.rollback();
        }finally {
            DbConfig.closeSession(session);
        }

    }

}
