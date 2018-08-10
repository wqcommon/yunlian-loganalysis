package com.yunlian.loganalysis.topology;

import com.yunlian.loganalysis.bolt.DataTransferBolt;
import com.yunlian.loganalysis.bolt.MysqlDataBolt;
import com.yunlian.loganalysis.config.properties.ConfigPropertiesFactory;
import com.yunlian.loganalysis.spout.KafkaDataSpout;
import com.yunlian.loganalysis.util.LogAnalysisConstant;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qiang.wen
 * @date 2018/8/7 10:59
 * <p>
 * 日志分析拓扑
 */
public class LogAnalysisTopology {

    private static final Logger log = LoggerFactory.getLogger(LogAnalysisTopology.class);

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            log.error("errMsg:{}", "请设置args参数！");
            System.exit(0);
        }
        //加载配置文件
        String env = args[0];//环境参数
        ConfigPropertiesFactory.loadProperties(env);

        //定义topology以及提交topology
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        //定义KafkaSpout
        String kafkaSpoutId = LogAnalysisConstant.KAFKASPOUT_PREFIX + env;
        topologyBuilder.setSpout(kafkaSpoutId, new KafkaDataSpout(), LogAnalysisConstant.PARALLEL_NUM).setNumTasks(LogAnalysisConstant.TASKS_NUM);

        //定义DataTransferBolt
        String dataTransferBoltId = LogAnalysisConstant.DATATRANSFERBOLT_PREFIX + env;
        topologyBuilder.setBolt(dataTransferBoltId,new DataTransferBolt(),LogAnalysisConstant.PARALLEL_NUM).shuffleGrouping(kafkaSpoutId);

        //定义MysqlDataBolt
        String mysqlDataBoltId = LogAnalysisConstant.MYSQLDATABOLT_PREFIX + env;
        topologyBuilder.setBolt(mysqlDataBoltId,new MysqlDataBolt(),LogAnalysisConstant.PARALLEL_NUM).shuffleGrouping(dataTransferBoltId);

        //设置topology在集群运行时的参数
        Config conf = new Config();
        conf.setNumWorkers(LogAnalysisConstant.WORKERS_NUM);
        if("local".equals(env)){
            conf.setDebug(true);
            conf.setNumAckers(0);
        }
        //创建topology
        StormTopology topology = topologyBuilder.createTopology();

        //topologyName
        String topologyName = LogAnalysisConstant.TOPOLOGY_LOGANALYSIS + env;
        if("local".equals(env)){
            //本地模式
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology(topologyName,conf,topology);
        }else{
            //集群模式
            try {
                StormSubmitter.submitTopology(topologyName,conf,topology);
            } catch (Exception e) {
                log.error("提交topology失败，e:{}",e);
            }
        }


    }

}
