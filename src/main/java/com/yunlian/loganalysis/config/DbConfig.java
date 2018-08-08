package com.yunlian.loganalysis.config;

import com.yunlian.loganalysis.config.properties.ConfigPropertiesFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qiang.wen
 * @date 2018/8/7 11:19
 *
 * 数据库config
 */
public class DbConfig {

    private static final Logger log = LoggerFactory.getLogger(DbConfig.class);

    //mybatis的配置文件
    public static final String MYBATIS_CONFIG_PATH = "mybatis-config.xml";

    private static SqlSessionFactory sqlSessionFactory;

    private static Lock lock = new ReentrantLock();

    /**
     * 初始化sqlsessionFactory
     */
    public static void initSqlSessionFacotry(){
        lock.lock();
        try{
            if(Objects.nonNull(sqlSessionFactory)){
                return;
            }
            InputStream is = Resources.getResourceAsStream(MYBATIS_CONFIG_PATH);
            Properties dbProperties = ConfigPropertiesFactory.getDbProperties();
            String env = dbProperties.getProperty(ConfigPropertiesFactory.ConfigKey.KEY_DB_ENV);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is,env,dbProperties);
        } catch (IOException e) {
            log.error("加载mybatis-config.xml配置文件失败，e:{}",e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取sqlSession
     * @return
     */
    public static SqlSession openSqlSession(boolean autoCommit){
        if(Objects.isNull(sqlSessionFactory)){
            initSqlSessionFacotry();
        }
        return sqlSessionFactory.openSession(autoCommit);
    }


    /**
     * 关闭sqlSession
     * @param sqlSession
     */
    public static void closeSession(SqlSession sqlSession){
        if(Objects.nonNull(sqlSession)){
            sqlSession.close();
        }
    }
}
