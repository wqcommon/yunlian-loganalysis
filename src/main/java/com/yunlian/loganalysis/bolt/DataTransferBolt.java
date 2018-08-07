package com.yunlian.loganalysis.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/7 17:24
 *
 *  数据转换bolt，主要功能：
 *  1、把kafka的消息转换为结构化数据
 */
public class DataTransferBolt extends BaseRichBolt{

    private static final long serialVersionUID = 4429523429657263619L;

    private OutputCollector outputCollector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
