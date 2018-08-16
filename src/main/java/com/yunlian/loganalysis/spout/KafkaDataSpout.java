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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private Lock lock = new ReentrantLock();


    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        //初始化kafka
        //环境
        String env = topologyContext.getThisComponentId().substring(LogAnalysisConstant.KAFKASPOUT_PREFIX.length());
        initKafka(env);
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
                    log.info("kafkaSpout发送的数据的长度：{},当前时间：{}",dataList.size(), LocalDateTime.now());
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
     * 当tuple处理失败后，Spout调用fail方法
     * @param msgId
     */
    @Override
    public void fail(Object msgId) {
       //目前不做任何处理
    }

    /**
     * 当tuple处理成功后，Spout调用ack方法
     * @param msgId
     */
    @Override
    public void ack(Object msgId) {
        //目前不在任何处理
    }

    /**
     * 初始化kafka
     */
    private void initKafka(String env) {
        lock.lock();
        try{
            if(Objects.nonNull(kafkaConsumer)){
                return;
            }
            KafkaProperties kafkaProperties = ConfigPropertiesFactory.loadKafkaProperties(env);
            kafkaConsumer = KafkaConfig.initKafkaConsumer(kafkaProperties);
            //订阅topic
            String consumerTopics = kafkaProperties.getConsumerTopics();
            kafkaConsumer.subscribe(Arrays.asList(consumerTopics.split(",")));
        }finally {
            lock.unlock();
        }

    }
}
