package com.yunlian.loganalysis.service;

/**
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


}
