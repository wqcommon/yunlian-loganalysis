package com.yunlian.loganalysis.spout;

import com.yunlian.loganalysis.config.KafkaConfig;
import com.yunlian.loganalysis.config.properties.ConfigPropertiesFactory;
import com.yunlian.loganalysis.config.properties.KafkaProperties;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/7 16:57
 * kafka数据spout
 */
public class KafkaDataSpout extends BaseRichSpout{

    private static final long serialVersionUID = -3970348599175813004L;

    private Logger log = LoggerFactory.getLogger(KafkaDataSpout.class);


    private SpoutOutputCollector spoutOutputCollector;

    private KafkaConsumer<String,String> kafkaConsumer;


    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        //初始化kafka
        initKafka();
        this.spoutOutputCollector = spoutOutputCollector;
    }


    @Override
    public void nextTuple() {


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    private void initKafka() {
        KafkaProperties kafkaProperties = ConfigPropertiesFactory.loadKafkaProperties();
        kafkaConsumer = KafkaConfig.initKafkaConsumer(kafkaProperties);
        //订阅topic
        String consumerTopics = kafkaProperties.getConsumerTopics();
        kafkaConsumer.subscribe(Arrays.asList(consumerTopics.split(",")));
    }
}
