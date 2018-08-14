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
        private static final String KEY_INITIAL_SIZE = "initialSize";
        private static final String KEY_MIN_IDLE = "minIdle";
        private static final String KEY_MAX_ACTIVE = "maxActive";
        private static final String KEY_MAX_WAIT = "maxWait";
        private static final String KEY_TEST_ON_BORROW = "testOnBorrow";
        private static final String KEY_TEST_WHILE_IDLE = "testWhileIdle";
        private static final String KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS= "timeBetweenEvictionRunsMillis";
        private static final String KEY_VALIDATION_QUERY = "validationQuery";

    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getProperty(DBKey.KEY_DRIVERCLASSNAME));
        dataSource.setUrl(properties.getProperty(DBKey.KEY_URL));
        dataSource.setUsername(properties.getProperty(DBKey.KEY_USERNAME));
        dataSource.setPassword(properties.getProperty(DBKey.KEY_PASSWORD));
        dataSource.setInitialSize(Integer.parseInt(properties.getProperty(DBKey.KEY_INITIAL_SIZE,"5")));
        dataSource.setMinIdle(Integer.parseInt(properties.getProperty(DBKey.KEY_MIN_IDLE,"5")));
        dataSource.setMaxActive(Integer.parseInt(properties.getProperty(DBKey.KEY_MAX_ACTIVE,"20")));
        dataSource.setMaxWait(Long.parseLong(properties.getProperty(DBKey.KEY_MAX_WAIT,"2000")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty(DBKey.KEY_TEST_ON_BORROW,"true")));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty(DBKey.KEY_TEST_WHILE_IDLE,"true")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(properties.getProperty(DBKey.KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS,"60000")));
        dataSource.setValidationQuery(properties.getProperty(DBKey.KEY_VALIDATION_QUERY));
        return dataSource;
    }
}
