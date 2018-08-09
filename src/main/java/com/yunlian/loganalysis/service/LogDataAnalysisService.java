package com.yunlian.loganalysis.service;

import com.yunlian.loganalysis.dto.StatOriginLogDataDto;

import java.util.List; /**
 * @author qiang.wen
 * @date 2018/8/9 14:53
 *
 * 日志数据分析处理service
 */
public class LogDataAnalysisService {

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


    }
}
