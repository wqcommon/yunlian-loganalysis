package com.yunlian.loganalysis.config.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author qiang.wen
 * @date 2018/8/7 11:20
 *
 *  配置文件加载类
 */
public class ConfigPropertiesFactory {

    private static final Logger log = LoggerFactory.getLogger(ConfigPropertiesFactory.class);

    private static final String FORMAT_CONFIGPROPERTIES = "/config-%s.properties";

    private static Properties configProperties;



    class ConfigKey{

        //kafka配置key
        private static final String KEY_KAFKA_BOOTSTRAP_SERVERS = "kafka.bootstrap.servers";
        private static final String KEY_KAFKA_GROUP_ID = "kafka.group.id";
        private static final String KEY_KAFKA_ENABLE_AUTO_COMMIT = "kafka.enable.auto.commit";
        private static final String KEY_KAFKA_AUTO_COMMIT_INTERVAL_MS = "kafka.auto.commit.interval.ms";
        private static final String KEY_KAFKA_KEY_DESERIALIZER = "kafka.key.deserializer";
        private static final String KEY_KAFKA_VALUE_DESERIALIZER = "kafka.value.deserializer";
        private static final String KEY_KAFKA_MAX_POLL_RECORDS = "kafka.max.poll.records";
        private static final String KEY_KAFKA_CONSUMER_TOPICS = "kafka.consumer.topics";


    }

    /**
     * 加载配置文件
     * @param env
     */
    public static void loadProperties(String env) {
        //加载配置文件
        String configPath = String.format(FORMAT_CONFIGPROPERTIES,env);
        InputStream is = ConfigPropertiesFactory.class.getResourceAsStream(configPath);
        configProperties = new Properties();
        try {
            configProperties.load(is);
        } catch (IOException e) {
            log.error("加载配置文件错误！");
        }

    }

    public static Properties getConfigProperties(){
        return configProperties;
    }

    /**
     * 获取kafka配置参数
     * @return
     */
    public static KafkaProperties loadKafkaProperties() {
        KafkaProperties kafkaProperties = new KafkaProperties();
        kafkaProperties.setAutoCommitIntervalMs(Integer.parseInt(configProperties.getProperty(ConfigKey.KEY_KAFKA_AUTO_COMMIT_INTERVAL_MS,"1000")));
        kafkaProperties.setBootstrapServers(configProperties.getProperty(ConfigKey.KEY_KAFKA_BOOTSTRAP_SERVERS));
        kafkaProperties.setEnableAutoCommit(Boolean.parseBoolean(configProperties.getProperty(ConfigKey.KEY_KAFKA_ENABLE_AUTO_COMMIT,"true")));
        kafkaProperties.setGroupId(configProperties.getProperty(ConfigKey.KEY_KAFKA_GROUP_ID));
        kafkaProperties.setKeyDeserializer(configProperties.getProperty(ConfigKey.KEY_KAFKA_KEY_DESERIALIZER));
        kafkaProperties.setValueDeserializer(configProperties.getProperty(ConfigKey.KEY_KAFKA_VALUE_DESERIALIZER));
        kafkaProperties.setMaxPollRecords(Integer.parseInt(configProperties.getProperty(ConfigKey.KEY_KAFKA_MAX_POLL_RECORDS,"100")));
        kafkaProperties.setConsumerTopics(configProperties.getProperty(ConfigKey.KEY_KAFKA_CONSUMER_TOPICS));
        return kafkaProperties;
    }
}
