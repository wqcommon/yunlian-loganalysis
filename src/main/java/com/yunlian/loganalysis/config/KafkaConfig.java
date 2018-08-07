package com.yunlian.loganalysis.config;

import com.yunlian.loganalysis.config.properties.KafkaProperties;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiang.wen
 * @date 2018/8/7 11:19
 */
public class KafkaConfig {

    /**
     * 初始化KafkaConsumer
     * @return
     */
    public static KafkaConsumer<String,String> initKafkaConsumer(KafkaProperties kafkaProperties) {
        Map<String,Object> kafkaConfig = new HashMap<>();
        kafkaConfig.put("bootstrap.servers",kafkaProperties.getBootstrapServers());
        kafkaConfig.put("group.id",kafkaProperties.getGroupId());
        kafkaConfig.put("enable.auto.commit",kafkaProperties.isEnableAutoCommit());
        kafkaConfig.put("auto.commit.interval.ms",kafkaProperties.getAutoCommitIntervalMs());
        kafkaConfig.put("key.deserializer",kafkaProperties.getKeyDeserializer());
        kafkaConfig.put("value.deserializer",kafkaProperties.getValueDeserializer());
        kafkaConfig.put("max.poll.records",kafkaProperties.getMaxPollRecords());
        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer(kafkaConfig);
        return kafkaConsumer;
    }
}
