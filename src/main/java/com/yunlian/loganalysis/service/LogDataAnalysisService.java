package com.yunlian.loganalysis.service;

import com.yunlian.loganalysis.config.DbConfig;
import com.yunlian.loganalysis.convertor.LogDataConvertor;
import com.yunlian.loganalysis.dao.StatOriginLogDataDao;
import com.yunlian.loganalysis.dto.StatOriginLogDataDto;
import com.yunlian.loganalysis.po.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.storm.shade.org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List; /**
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

    private void handleStatCallDailyData(SqlSession sqlSession, List<StatCallDailyPo> statCallDailyPos) {
        
    }

    /**
     * 处理每个Api的调用情况
     * @param sqlSession
     * @param statCallApiPos
     */
    private void handleStatCallApiData(SqlSession sqlSession, List<StatCallApiPo> statCallApiPos) {
        if(CollectionUtils.isEmpty(statCallApiPos)){
            return;
        }

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
