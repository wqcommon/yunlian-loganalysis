package com.yunlian.loganalysis.config.properties;

import java.util.Properties;

/**
 * @author qiang.wen
 * @date 2018/8/7 11:22
 */
public class DbProperties {

    //驱动类
    public static final String KEY_DB_DRIVERCLASSNAME = "db.driverClassName";
    //url
    public static final String KEY_DB_URL = "db.url";
    //用户名
    public static final String KEY_DB_USERNAME = "db.username";
    //密码
    public static final String KEY_DB_PASSWORD = "db.password";


    /**
     * 加载数据库的配置参数
     * @return
     */
    public static Properties loadDbProperties(){
        Properties dbProperties = new Properties();
        //所有的配置项
        Properties configProperties = ConfigPropertiesFactory.getConfigProperties();
        dbProperties.setProperty(KEY_DB_DRIVERCLASSNAME,configProperties.getProperty(KEY_DB_DRIVERCLASSNAME));
        dbProperties.setProperty(KEY_DB_URL,configProperties.getProperty(KEY_DB_URL));
        dbProperties.setProperty(KEY_DB_USERNAME,configProperties.getProperty(KEY_DB_USERNAME));
        dbProperties.setProperty(KEY_DB_PASSWORD,configProperties.getProperty(KEY_DB_PASSWORD));
        return dbProperties;
    }
}
