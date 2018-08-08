package com.yunlian.loganalysis;

import com.yunlian.loganalysis.config.properties.ConfigPropertiesFactory;
import com.yunlian.loganalysis.service.TbTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author qiang.wen
 * @date 2018/8/8 14:35
 */
public class LogMain {

    private static final Logger log = LoggerFactory.getLogger(LogMain.class);

    public static void main(String[] args) {
        if(args == null || args.length == 0){
            log.error("errMsg:{}","请设置args参数！");
            System.exit(0);
        }
        //加载配置文件
        ConfigPropertiesFactory.loadProperties(args[0]);

        TbTestService tbTestService = TbTestService.getInstance();
//        tbTestService.query("w", BigDecimal.ONE);
        tbTestService.insertData();
    }
}
