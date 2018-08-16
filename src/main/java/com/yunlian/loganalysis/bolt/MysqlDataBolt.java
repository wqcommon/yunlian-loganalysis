package com.yunlian.loganalysis.bolt;

import com.alibaba.fastjson.JSONObject;
import com.yunlian.loganalysis.config.properties.ConfigPropertiesFactory;
import com.yunlian.loganalysis.dto.StatOriginLogDataDto;
import com.yunlian.loganalysis.service.LogDataAnalysisService;
import com.yunlian.loganalysis.util.LogAnalysisConstant;
import org.apache.storm.shade.org.apache.commons.collections.CollectionUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/7 17:26
 * mysql数据处理Bolt，主要功能:
 * 1、将DataTransferBolt处理的结构化数据存储在mysql中
 */
public class MysqlDataBolt extends BaseRichBolt{

    private static final Logger log = LoggerFactory.getLogger(MysqlDataBolt.class);

    private static final long serialVersionUID = 7224684733827266594L;

    private OutputCollector outputCollector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        //环境
        String env = topologyContext.getThisComponentId().substring(LogAnalysisConstant.MYSQLDATABOLT_PREFIX.length());
        //加载数据库的配置文件
        ConfigPropertiesFactory.loadDbProPerties(env);
        this.outputCollector = outputCollector;

    }

    @Override
    public void execute(Tuple tuple) {
        String data = (String) tuple.getValueByField(LogAnalysisConstant.FIELD_DATATRANSFER);
        List<StatOriginLogDataDto> logDataDtos = JSONObject.parseArray(data,StatOriginLogDataDto.class);
        if(CollectionUtils.isNotEmpty(logDataDtos)){
            //处理数据入库
            try {
                LogDataAnalysisService dataAnalysisService = LogDataAnalysisService.getInstance();
                dataAnalysisService.statLogData(logDataDtos);
                outputCollector.ack(tuple);
            }catch (Exception e){
                log.error("日志统计数据入库失败：e:{}",e);
                outputCollector.fail(tuple);
            }

        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //此处不需要继续往下发送数据了
    }
}
