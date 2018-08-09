package com.yunlian.loganalysis.service;

import com.yunlian.loganalysis.convertor.LogDataConvertor;
import com.yunlian.loganalysis.dto.StatOriginLogDataDto;
import com.yunlian.loganalysis.po.*;
import org.apache.storm.shade.org.apache.commons.collections.CollectionUtils;

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
        if(CollectionUtils.isEmpty(logDataDtos)){
            return;
        }
        /**数据转换*/
        //转换为StatOriginLogDataPo
        List<StatOriginLogDataPo> originLogDataPos = LogDataConvertor.convertToStatOriginLogDataPos(logDataDtos);
        //转换为StatCallApiPo
        List<StatCallApiPo> statCallApiPos = LogDataConvertor.convertToStatCallApiPos(logDataDtos);
        //转换为StatCallDailyPo
        List<StatCallDailyPo> statCallDailyPos = LogDataConvertor.convertToStatCallDailyPos(logDataDtos);
        //转换为StatCallDailyApiPo
        List<StatCallDailyApiPo> statCallDailyApiPos = LogDataConvertor.convertToStatCallDailyApiPos(logDataDtos);
        //转换为StatCallPartnerDailyApiPo
        List<StatCallPartnerDailyApiPo> statCallPartnerDailyApiPos = LogDataConvertor.converttoStatCallPartnerDailyApiPos(logDataDtos);

    }
}
