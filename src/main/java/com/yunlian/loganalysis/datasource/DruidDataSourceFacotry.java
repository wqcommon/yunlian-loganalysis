package com.yunlian.loganalysis.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.yunlian.loganalysis.config.properties.DbProperties;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author qiang.wen
 * @date 2018/8/7 20:24
 */
public class DruidDataSourceFacotry implements DataSourceFactory{

    private Properties dbProperties;

    @Override
    public void setProperties(Properties properties) {
        this.dbProperties = properties;
    }


    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dbProperties.getProperty(DbProperties.KEY_DB_DRIVERCLASSNAME));
        dataSource.setUrl(dbProperties.getProperty(DbProperties.KEY_DB_URL));
        dataSource.setUsername(dbProperties.getProperty(DbProperties.KEY_DB_USERNAME));
        dataSource.setPassword(dbProperties.getProperty(DbProperties.KEY_DB_PASSWORD));
        //TODO 设置一些配置参数
        return dataSource;
    }
}
