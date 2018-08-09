package com.yunlian.loganalysis.bolt;

import com.alibaba.fastjson.JSONObject;
import com.yunlian.loganalysis.convertor.LogDataConvertor;
import com.yunlian.loganalysis.dto.StatOriginLogDataDto;
import com.yunlian.loganalysis.util.LogAnalysisConstant;
import org.apache.storm.shade.org.apache.commons.collections.CollectionUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author qiang.wen
 * @date 2018/8/7 17:24
 *
 *  数据转换bolt，主要功能：
 *  1、把kafka的消息转换为结构化数据
 */
public class DataTransferBolt extends BaseRichBolt{

    private static final Logger log = LoggerFactory.getLogger(DataTransferBolt.class);

    private static final long serialVersionUID = 4429523429657263619L;

    private OutputCollector outputCollector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        //从kafkaSpout中获取信息
       String originData = (String) tuple.getValueByField(LogAnalysisConstant.FIELD_KAFKAMSG);
       List<String> originDataList = JSONObject.parseArray(originData,String.class);
       if (CollectionUtils.isEmpty(originDataList)){
           return;
       }
       log.info("本次待处理的数据条数：{},当前时间：{}",originDataList.size(), LocalDateTime.now());
        //把信息转换为结构化数据
       List<StatOriginLogDataDto> originLogDataDtos = new ArrayList<>();
       originDataList.forEach(data ->{
            StatOriginLogDataDto dto = LogDataConvertor.convertToStatOriginLogDataDto(data);
            if(Objects.nonNull(dto)){
                originLogDataDtos.add(dto);
            }
       });
       log.info("本次转换的数据条数：{}，当前时间：{}",originLogDataDtos.size(),LocalDateTime.now());
       if(originLogDataDtos.size() > 0){
           String dtoJsonString = JSONObject.toJSONString(originLogDataDtos);
           outputCollector.emit(new Values(dtoJsonString));
       }
       outputCollector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(LogAnalysisConstant.FIELD_DATATRANSFER));
    }
}
