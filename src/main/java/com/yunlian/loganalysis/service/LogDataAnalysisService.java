package com.yunlian.loganalysis.service;

import com.yunlian.loganalysis.config.DbConfig;
import com.yunlian.loganalysis.convertor.LogDataConvertor;
import com.yunlian.loganalysis.dao.*;
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
        //获取SqlSession
        SqlSession sqlSession = DbConfig.openSqlSession(false);
        //获取restful路径
        StatSysApiPathDao statSysApiPathDao = sqlSession.getMapper(StatSysApiPathDao.class);
        List<StatSysApiPathPo> apiPathPos = statSysApiPathDao.query(Collections.emptyMap());
        /**数据转换*/
        List<StatOriginLogDataPo> originLogDataPos = LogDataConvertor.convertToStatOriginLogDataPos(logDataDtos);

        List<StatCallApiPo> statCallApiPos = LogDataConvertor.convertToStatCallApiPos(logDataDtos,apiPathPos);

        List<StatCallDailyPo> statCallDailyPos = LogDataConvertor.convertToStatCallDailyPos(logDataDtos);

        List<StatCallDailyApiPo> statCallDailyApiPos = LogDataConvertor.convertToStatCallDailyApiPos(logDataDtos,apiPathPos);

        List<StatCallPartnerDailyApiPo> statCallPartnerDailyApiPos = LogDataConvertor.converttoStatCallPartnerDailyApiPos(logDataDtos,apiPathPos);

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

    /**
     * 处理每个调用方每天每个api的调用数据
     * @param sqlSession
     * @param statCallPartnerDailyApiPos
     */
    private void handleStatCallPartnerDailyApiData(SqlSession sqlSession, List<StatCallPartnerDailyApiPo> statCallPartnerDailyApiPos) {
        if(CollectionUtils.isEmpty(statCallPartnerDailyApiPos)){
            return;
        }
        StatCallPartnerDailyApiDao statCallPartnerDailyApiDao = sqlSession.getMapper(StatCallPartnerDailyApiDao.class);
        //根据appCode、statDate、apiUrl分组
        Map<String,List<StatCallPartnerDailyApiPo>> dataMap = statCallPartnerDailyApiPos.stream().collect(Collectors.groupingBy(po -> po.getAppCode() +"_" +po.getApiUrl() + " _" + po.getStatDate().toString()));
        //分组进行处理
        GlobalIdGenerator idGenerator = GlobalIdGenerator.getInstance();
        //待插入
        List<StatCallPartnerDailyApiPo> needInsertList = new ArrayList<>();
        //待更新
        List<StatCallPartnerDailyApiPo> needUpdateList = new ArrayList<>();
        Map<String,Object> queryMap = new HashMap<>(3);
        for(String key : dataMap.keySet()){
            List<StatCallPartnerDailyApiPo> poList = dataMap.get(key);
            StatCallPartnerDailyApiPo tPo = poList.get(0);
            //根据appCode\apiUrl、statDate查询
            queryMap.put("appCode",tPo.getAppCode());
            queryMap.put("statDate",tPo.getStatDate());
            queryMap.put("apiUrl",tPo.getApiUrl());
            StatCallPartnerDailyApiPo dbPo = statCallPartnerDailyApiDao.getByAppStatDateAndUrl(queryMap);
            //统计成功失败次数、响应时间
            long totalNum = 0,successNum = 0, failureNum = 0;
            long minResponseTime = tPo.getMinResponseTime();
            long maxResponseTime = tPo.getMaxResponseTime();
            for(StatCallPartnerDailyApiPo po : poList){
                totalNum += po.getTotalCallnum();
                successNum += po.getSuccessCallnum();
                failureNum += po.getFailureCallnum();
                if(po.getMinResponseTime() < minResponseTime){
                    minResponseTime = po.getMinResponseTime();
                }
                if(po.getMaxResponseTime() > maxResponseTime){
                    maxResponseTime = po.getMaxResponseTime();
                }
            }
            if(Objects.isNull(dbPo)){
                //新增
                tPo.setUid(String.valueOf(idGenerator.nextId()));
                tPo.setTotalCallnum(totalNum);
                tPo.setSuccessCallnum(successNum);
                tPo.setFailureCallnum(failureNum);
                tPo.setMinResponseTime(minResponseTime);
                tPo.setMaxResponseTime(maxResponseTime);
                needInsertList.add(tPo);
            }else {
                //更新
                dbPo.setTotalCallnum(totalNum);
                dbPo.setSuccessCallnum(successNum);
                dbPo.setFailureCallnum(failureNum);
                dbPo.setMinResponseTime(minResponseTime);
                dbPo.setMaxResponseTime(maxResponseTime);
                needUpdateList.add(dbPo);
            }
        }
        //db操作
        if(CollectionUtils.isNotEmpty(needInsertList)){
            statCallPartnerDailyApiDao.insertBatch(needInsertList);
        }
        needUpdateList.forEach(updatePo -> {
            statCallPartnerDailyApiDao.update(updatePo);
        });

    }

    /**
     * 处理每天每个api的调用数据
     * @param sqlSession
     * @param statCallDailyApiPos
     */
    private void handleStatCallDailyApiData(SqlSession sqlSession, List<StatCallDailyApiPo> statCallDailyApiPos) {
        if(CollectionUtils.isEmpty(statCallDailyApiPos)){
            return;
        }
        StatCallDailyApiDao statCallDailyApiDao = sqlSession.getMapper(StatCallDailyApiDao.class);
        //根据statDate和apiUrl分组
        Map<String,List<StatCallDailyApiPo>> dataMap = statCallDailyApiPos.stream().collect(Collectors.groupingBy(po -> po.getApiUrl() + " _" + po.getStatDate().toString()));
        //分组进行处理
        GlobalIdGenerator idGenerator = GlobalIdGenerator.getInstance();
        //待插入
        List<StatCallDailyApiPo> needInsertList = new ArrayList<>();
        //待更新
        List<StatCallDailyApiPo> needUpdateList = new ArrayList<>();
        Map<String,Object> queryMap = new HashMap<>(2);
        for(String key : dataMap.keySet()){
            List<StatCallDailyApiPo> poList = dataMap.get(key);
            StatCallDailyApiPo tmpPo = poList.get(0);
            //根据apiUrl和statDate查询
            queryMap.put("statDate",tmpPo.getStatDate());
            queryMap.put("apiUrl",tmpPo.getApiUrl());
            StatCallDailyApiPo dbPo = statCallDailyApiDao.getByStatDateAndUrl(queryMap);
            //统计成功失败次数、响应时间
            long totalNum = 0,successNum = 0, failureNum = 0;
            long minResponseTime = tmpPo.getMinResponseTime();
            long maxResponseTime = tmpPo.getMaxResponseTime();
            for(StatCallDailyApiPo po : poList){
                totalNum += po.getTotalCallnum();
                successNum += po.getSuccessCallnum();
                failureNum += po.getFailureCallnum();
                if(po.getMinResponseTime() < minResponseTime){
                    minResponseTime = po.getMinResponseTime();
                }
                if(po.getMaxResponseTime() > maxResponseTime){
                    maxResponseTime = po.getMaxResponseTime();
                }
            }
            if(Objects.isNull(dbPo)){
                //新增
                tmpPo.setUid(String.valueOf(idGenerator.nextId()));
                tmpPo.setTotalCallnum(totalNum);
                tmpPo.setSuccessCallnum(successNum);
                tmpPo.setFailureCallnum(failureNum);
                tmpPo.setMinResponseTime(minResponseTime);
                tmpPo.setMaxResponseTime(maxResponseTime);
                needInsertList.add(tmpPo);
            }else {
                //更新
                dbPo.setTotalCallnum(totalNum);
                dbPo.setSuccessCallnum(successNum);
                dbPo.setFailureCallnum(failureNum);
                dbPo.setMinResponseTime(minResponseTime);
                dbPo.setMaxResponseTime(maxResponseTime);
                needUpdateList.add(dbPo);
            }

        }
        //db操作
        if(CollectionUtils.isNotEmpty(needInsertList)){
            statCallDailyApiDao.insertBatch(needInsertList);
        }
        needUpdateList.forEach(updatePo -> {
            statCallDailyApiDao.update(updatePo);
        });
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
        //根据statDate集合查询记录
        Set<LocalDate> statDateSet = dataMap.keySet();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("statDateList",statDateSet);
        List<StatCallDailyPo> dbPos = statCallDailyDao.queryExcludeTime(queryMap);
        Map<LocalDate,StatCallDailyPo> dbPoMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(dbPos)){
            dbPoMap = dbPos.stream().collect(Collectors.toMap(po -> po.getStatDate(),po -> po, (newPo,oldPo) -> newPo ));
        }
        //分组进行处理
        //待插入列表
        List<StatCallDailyPo> needInsertList = new ArrayList<>();
        //待更新列表
        List<StatCallDailyPo> needUpdateList = new ArrayList<>();
        GlobalIdGenerator idGenerator = GlobalIdGenerator.getInstance();
        for(LocalDate statDate : statDateSet){
            StatCallDailyPo po = dbPoMap.get(statDate);
            List<StatCallDailyPo> poList = dataMap.get(statDate);
            //统计次数
            long totalNum = 0,successNum = 0, failureNum = 0;
            for (StatCallDailyPo dailyPo : poList){
                totalNum += dailyPo.getTotalCallnum();
                successNum += dailyPo.getSuccessCallnum();
                failureNum += dailyPo.getFailureCallnum();
            }
            if(Objects.isNull(po)){
                //db中不存在，需要待插入
                StatCallDailyPo tempPo = poList.get(0);
                tempPo.setUid(String.valueOf(idGenerator.nextId()));
                tempPo.setTotalCallnum(totalNum);
                tempPo.setSuccessCallnum(successNum);
                tempPo.setFailureCallnum(failureNum);
                needInsertList.add(tempPo);
            }else{
                //db中存在，需要更新
                po.setTotalCallnum(totalNum);
                po.setFailureCallnum(failureNum);
                po.setSuccessCallnum(successNum);
                needUpdateList.add(po);
            }
        }
        //新增
        if(CollectionUtils.isNotEmpty(needInsertList)){
            statCallDailyDao.insertBatch(needInsertList);
        }
        //更新
        needUpdateList.forEach(updatePo -> {
            statCallDailyDao.update(updatePo);
        });
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
            long minResponseTime = poList.get(0).getMinResponseTime();
            long maxResponseTime = poList.get(0).getMaxResponseTime();
            for (StatCallApiPo apiPo : poList){
                totalNum += apiPo.getTotalCallnum();
                successNum += apiPo.getSuccessCallnum();
                failureNum += apiPo.getFailureCallnum();
                if(apiPo.getMinResponseTime() < minResponseTime){
                    minResponseTime = apiPo.getMinResponseTime();
                }
                if(apiPo.getMaxResponseTime() > maxResponseTime){
                    maxResponseTime = apiPo.getMaxResponseTime();
                }
            }
            if(Objects.isNull(po)){
                //db中不存在，需要待插入
                StatCallApiPo tempPo = poList.get(0);
                tempPo.setUid(String.valueOf(idGenerator.nextId()));
                tempPo.setTotalCallnum(totalNum);
                tempPo.setSuccessCallnum(successNum);
                tempPo.setFailureCallnum(failureNum);
                tempPo.setMinResponseTime(minResponseTime);
                tempPo.setMaxResponseTime(maxResponseTime);
                needInsertList.add(tempPo);
            }else{
                //db中存在，需要更新
                po.setTotalCallnum(totalNum);
                po.setFailureCallnum(failureNum);
                po.setSuccessCallnum(successNum);
                po.setMinResponseTime(minResponseTime);
                po.setMaxResponseTime(maxResponseTime);
                needUpdateList.add(po);
            }
        }
        //新增
        if(CollectionUtils.isNotEmpty(needInsertList)){
            statCallApiDao.insertBatch(needInsertList);
        }
        //更新
        needUpdateList.forEach(updatePo -> {
            statCallApiDao.update(updatePo);
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
