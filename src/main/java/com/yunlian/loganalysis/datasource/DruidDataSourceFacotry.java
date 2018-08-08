package com.yunlian.loganalysis.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author qiang.wen
 * @date 2018/8/7 20:24
 */
public class DruidDataSourceFacotry implements DataSourceFactory{

    private static final Logger log = LoggerFactory.getLogger(DruidDataSourceFacotry.class);

    private Properties properties;

    @Override
    public void setProperties(Properties properties) {
        //该properties的key与mybatis-config.xml中<dataSource>中的property的name对应
        this.properties = properties;
    }

    private class DBKey{

        private static final String KEY_DRIVERCLASSNAME = "driverClass";
        private static final String KEY_URL = "url";
        private static final String KEY_USERNAME = "username";
        private static final String KEY_PASSWORD = "password";
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getProperty(DBKey.KEY_DRIVERCLASSNAME));
        dataSource.setUrl(properties.getProperty(DBKey.KEY_URL));
        dataSource.setUsername(properties.getProperty(DBKey.KEY_USERNAME));
        dataSource.setPassword(properties.getProperty(DBKey.KEY_PASSWORD));
        //TODO 设置一些配置参数
        return dataSource;
    }
}
