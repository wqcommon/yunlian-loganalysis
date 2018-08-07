package com.yunlian.loganalysis.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/7 17:26
 * mysql数据处理Bolt，主要功能:
 * 1、将DataTransferBolt处理的结构化数据存储在mysql中
 */
public class MysqlDataBolt extends BaseRichBolt{


    private static final long serialVersionUID = 7224684733827266594L;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    @Override
    public void execute(Tuple tuple) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
