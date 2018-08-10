package com.yunlian.loganalysis.service;

import com.yunlian.loganalysis.config.DbConfig;
import com.yunlian.loganalysis.convertor.LogDataConvertor;
import com.yunlian.loganalysis.dao.StatCallApiDao;
import com.yunlian.loganalysis.dao.StatCallDailyDao;
import com.yunlian.loganalysis.dao.StatOriginLogDataDao;
import com.yunlian.loganalysis.dto.StatOriginLogDataDto;
import com.yunlian.loganalysis.po.*;
import com.yunlian.loganalysis.util.GlobalIdGenerator;
import org.apache.ibatis.session.SqlSession;
import org.apache.storm.shade.org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qiang.wen
 * @date 2018/8/9 14:53
 *
 * 日志数据分析处理service
 */
public class LogDataAnalysisService {

    private Logger log = LoggerFactory.getLogger(LogDataAnalysisService.class);

    private static LogDataAnalysisService logDataAnalysisService = new LogDataAnalysisService();

    private LogDataAnalysisService(){}

    public static LogDataAnalysisService getInstance(){
        return logDataAnalysisService;
    }


    /**
     * 统计日志数据
     * @param logDataDtos
     */
    public void statLogData(List<StatOriginLogDataDto> logDataDtos) {
        if(CollectionUtils.isEmpty(logDataDtos)){
            return;
        }
        /**数据转换*/
        List<StatOriginLogDataPo> originLogDataPos = LogDataConvertor.convertToStatOriginLogDataPos(logDataDtos);

        List<StatCallApiPo> statCallApiPos = LogDataConvertor.convertToStatCallApiPos(logDataDtos);

        List<StatCallDailyPo> statCallDailyPos = LogDataConvertor.convertToStatCallDailyPos(logDataDtos);

        List<StatCallDailyApiPo> statCallDailyApiPos = LogDataConvertor.convertToStatCallDailyApiPos(logDataDtos);

        List<StatCallPartnerDailyApiPo> statCallPartnerDailyApiPos = LogDataConvertor.converttoStatCallPartnerDailyApiPos(logDataDtos);

        //获取SqlSession
        SqlSession sqlSession = DbConfig.openSqlSession(false);
        try{
            /**数据处理*/
            handleOriginLogData(sqlSession,originLogDataPos);

            handleStatCallApiData(sqlSession,statCallApiPos);

            handleStatCallDailyData(sqlSession,statCallDailyPos);

            handleStatCallDailyApiData(sqlSession,statCallDailyApiPos);

            handleStatCallPartnerDailyApiData(sqlSession,statCallPartnerDailyApiPos);

            sqlSession.commit();
        }catch (Exception e){
            log.error("数据处理失败，e:{}",e);
            sqlSession.rollback();
        }finally {
            DbConfig.closeSession(sqlSession);
        }

    }

    private void handleStatCallPartnerDailyApiData(SqlSession sqlSession, List<StatCallPartnerDailyApiPo> statCallPartnerDailyApiPos) {

    }

    private void handleStatCallDailyApiData(SqlSession sqlSession, List<StatCallDailyApiPo> statCallDailyApiPos) {
        
    }

    /**
     * 处理每天的api的调用数据
     * @param sqlSession
     * @param statCallDailyPos
     */
    private void handleStatCallDailyData(SqlSession sqlSession, List<StatCallDailyPo> statCallDailyPos) {
        if(CollectionUtils.isEmpty(statCallDailyPos)){
            return;
        }
        StatCallDailyDao statCallDailyDao = sqlSession.getMapper(StatCallDailyDao.class);
        //根据statDate分组
        Map<LocalDate,List<StatCallDailyPo>> dataMap = statCallDailyPos.stream().collect(Collectors.groupingBy(po -> po.getStatDate()));

        
    }

    /**
     * 处理每个Api的调用数据
     * @param sqlSession
     * @param statCallApiPos
     */
    private void handleStatCallApiData(SqlSession sqlSession, List<StatCallApiPo> statCallApiPos) {
        if(CollectionUtils.isEmpty(statCallApiPos)){
            return;
        }
        StatCallApiDao statCallApiDao = sqlSession.getMapper(StatCallApiDao.class);
        //根据apiUrl分组
        Map<String,List<StatCallApiPo>> dataMap = statCallApiPos.stream().collect(Collectors.groupingBy(po -> po.getApiUrl()));
        //根据apiUrl查询列表
        Set<String> apiUrlSet = dataMap.keySet();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("apiUrlList",apiUrlSet);
        List<StatCallApiPo> dbPos = statCallApiDao.queryExcludeTime(queryMap);
        Map<String,StatCallApiPo> dbPoMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(dbPos)){
            dbPoMap = dbPos.stream().collect(Collectors.toMap(po -> po.getApiUrl(),po -> po, (newPo,oldPo) -> newPo ));
        }
        //分组进行处理
        //待插入列表
        List<StatCallApiPo> needInsertList = new ArrayList<>();
        //待更新列表
        List<StatCallApiPo> needUpdateList = new ArrayList<>();
        GlobalIdGenerator idGenerator = GlobalIdGenerator.getInstance();
        for(String apiUrl : apiUrlSet){
            StatCallApiPo po = dbPoMap.get(apiUrl);
            List<StatCallApiPo> poList = dataMap.get(apiUrl);
            //统计次数
            long totalNum = 0,successNum = 0, failureNum = 0;
            for (StatCallApiPo apiPo : poList){
                totalNum += apiPo.getTotalCallnum();
                successNum += apiPo.getSuccessCallnum();
                failureNum += apiPo.getFailureCallnum();
            }
            if(Objects.isNull(po)){
                //db中不存在，需要待插入
                StatCallApiPo tempPo = poList.get(0);
                tempPo.setUid(String.valueOf(idGenerator.nextId()));
                tempPo.setTotalCallnum(totalNum);
                tempPo.setSuccessCallnum(successNum);
                tempPo.setFailureCallnum(failureNum);
                needInsertList.add(tempPo);
            }else{
                //db中存在，需要更新
                po.setTotalCallnum(po.getTotalCallnum() + totalNum);
                po.setFailureCallnum(po.getFailureCallnum() + failureNum);
                po.setSuccessCallnum(po.getSuccessCallnum() + successNum);
                needUpdateList.add(po);
            }
        }
        //新增
        statCallApiDao.insertBatch(needInsertList);
        //更新
        needUpdateList.forEach(updatePo -> {
            statCallApiDao.updateByApiUrl(updatePo);
        });
    }

    /**
     * 处理日志源数据
     * @param sqlSession
     * @param originLogDataPos
     */
    private void handleOriginLogData(SqlSession sqlSession, List<StatOriginLogDataPo> originLogDataPos) {

        if (CollectionUtils.isNotEmpty(originLogDataPos)){
            //入库，构造的时候已经存在uid了
            StatOriginLogDataDao originLogDataDao = sqlSession.getMapper(StatOriginLogDataDao.class);
            originLogDataDao.insertBatch(originLogDataPos);
        }
        
    }
}
