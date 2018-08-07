package com.yunlian.loganalysis.topology;

import com.yunlian.loganalysis.config.properties.ConfigPropertiesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qiang.wen
 * @date 2018/8/7 10:59
 *
 * 日志分析拓扑
 */
public class LogAnalysisTopology {

    private static final Logger log = LoggerFactory.getLogger(LogAnalysisTopology.class);

    public static void main(String[] args) {
        if(args == null || args.length == 0){
            log.error("errMsg:{}","请设置args参数！");
            System.exit(0);
        }
        //加载配置文件
        ConfigPropertiesFactory.loadProperties(args[0]);

        //定义topology以及提交topology




    }

}
