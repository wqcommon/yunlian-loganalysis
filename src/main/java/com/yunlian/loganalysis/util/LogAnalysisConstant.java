package com.yunlian.loganalysis.util;

/**
 * @author qiang.wen
 * @date 2018/8/7 19:27
 * 常量类
 */
public class LogAnalysisConstant {

    //kafkaSpout向外输出的字段名称
    public static final String FIELD_KAFKAMSG = "kafkaMsgField";

    //DataTransferBolt向外输出的字段名称
    public static final String FIELD_DATATRANSFER = "dataTransferField";

    //kafkaSpout前缀
    public static final String KAFKASPOUT_PREFIX = "kafkaDataSpout_";

    //dataTransferBolt前缀
    public static final String DATATRANSFERBOLT_PREFIX = "dataTransferBolt_";

    //mysqlDataBolt前缀
    public static final String MYSQLDATABOLT_PREFIX = "mysqlDataBolt_";

    //并行数
    public static final int PARALLEL_NUM = 6;

    //任务数
    public static final int TASKS_NUM = 6;

    //worker数
    public static final int WORKERS_NUM = 6;

    //日志分析拓扑
    public static final String TOPOLOGY_LOGANALYSIS = "logAnalysisTopology_";
}
