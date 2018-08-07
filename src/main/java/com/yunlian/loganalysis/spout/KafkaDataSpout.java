package com.yunlian.loganalysis.spout;

import com.alibaba.fastjson.JSONObject;
import com.yunlian.loganalysis.config.KafkaConfig;
import com.yunlian.loganalysis.config.properties.ConfigPropertiesFactory;
import com.yunlian.loganalysis.config.properties.KafkaProperties;
import com.yunlian.loganalysis.util.LogAnalysisConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        while (true){
            try{
                ConsumerRecords<String,String> records = kafkaConsumer.poll(100);
                if(Objects.nonNull(records) && !records.isEmpty()){
                    List<String> dataList = new ArrayList<String>(records.count());
                    for (ConsumerRecord<String,String> record : records){
                        String value = record.value();
                        if(StringUtils.isNotBlank(value)){
                            dataList.add(value);
                        }else{
                            continue;
                        }
                    }
                    String jsonMsg = JSONObject.toJSONString(dataList);
                    log.info("kafkaSpout发送的数据：{},当前时间：{}",jsonMsg, LocalDateTime.now());
                    spoutOutputCollector.emit(new Values(jsonMsg));

                }else{
                    log.info("kafka本次未拉取到消息，当前时间：{}",LocalDateTime.now());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }catch (InterruptedException ie){

                    }
                }

            }catch (Exception e){
                log.error("kafka拉取消息失败：{},当前时间：{}",e,LocalDateTime.now());
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(LogAnalysisConstant.FIELD_KAFKAMSG));
    }


    /**
     * 初始化kafka
     */
    private void initKafka() {
        KafkaProperties kafkaProperties = ConfigPropertiesFactory.loadKafkaProperties();
        kafkaConsumer = KafkaConfig.initKafkaConsumer(kafkaProperties);
        //订阅topic
        String consumerTopics = kafkaProperties.getConsumerTopics();
        kafkaConsumer.subscribe(Arrays.asList(consumerTopics.split(",")));
    }
}
