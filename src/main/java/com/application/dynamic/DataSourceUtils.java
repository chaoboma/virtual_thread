package com.application.dynamic;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import javax.annotation.Resource;
import java.sql.SQLException;
//import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: TODO：数据源工具类
 * @Author: yyalin
 * @CreateDate: 2023/7/16 15:00
 * @Version: V1.0
 */
@Slf4j
@Component
public class DataSourceUtils {
    @Autowired
    DynamicDataSource dynamicDataSource;
    private ConcurrentHashMap<Object, Object> defineTargetDataSources;
    private final ReentrantLock lock = new ReentrantLock();
    /**
     * @Description: 根据传递的数据源信息测试数据库连接
     * @Author zhangyu
     */
    public DruidDataSource createDataSourceConnection(DataSourceInfo dataSourceInfo,String datasourceKey) {
        try {

            defineTargetDataSources = dynamicDataSource.getDefineTargetDataSources();
            DruidDataSource druidDataSourceTmp = null;
            druidDataSourceTmp = (DruidDataSource)defineTargetDataSources.get(datasourceKey);
            if(druidDataSourceTmp == null){
                DruidDataSource druidDataSource = new DruidDataSource();
                druidDataSource.setUrl(dataSourceInfo.getUrl());
                druidDataSource.setUsername(dataSourceInfo.getUserName());
                druidDataSource.setPassword(dataSourceInfo.getPassword());
                druidDataSource.setDriverClassName(dataSourceInfo.getDriverClassName());
                druidDataSource.setBreakAfterAcquireFailure(true);
                druidDataSource.setConnectionErrorRetryAttempts(0);
                druidDataSource.setMaxActive(2000);
                druidDataSource.setMinIdle(5);
                druidDataSource.setInitialSize(10);
                druidDataSource.setTestWhileIdle(false);
                druidDataSource.setTestOnBorrow(false);
                druidDataSource.setTestOnReturn(false);
                druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
                druidDataSource.getConnection(200);
                //将新的数据源连接添加到目标数据源map中
                DruidDataSource druidDataSourceOld = addDefineDynamicDataSource(druidDataSource,datasourceKey);

                //System.out.println(druidDataSourceOld.hashCode());
                return druidDataSourceOld;
            }else{
                return druidDataSourceTmp;
            }

        } catch (SQLException throwables) {
            //log.error("数据源 {} 连接失败,用户名：{}，密码 {}",dataSourceInfo.getUrl(),dataSourceInfo.getUserName(),dataSourceInfo.getPassword());
            return null;
        }
    }

    /**
     * @Description: 将新增的数据源加入到备份数据源map中
     * @Author zhangyu
     */
    public DruidDataSource addDefineDynamicDataSource(DruidDataSource druidDataSource, String dataSourceName){
        lock.lock();  // block until condition holds
        try {
            defineTargetDataSources = dynamicDataSource.getDefineTargetDataSources();
            if(!defineTargetDataSources.containsKey(dataSourceName)){
                defineTargetDataSources.put(dataSourceName, druidDataSource);
                dynamicDataSource.setTargetDataSources(defineTargetDataSources);
                dynamicDataSource.afterPropertiesSet();
                return druidDataSource;
            }else {
                return (DruidDataSource)defineTargetDataSources.get(dataSourceName);
            }
        } finally {
            lock.unlock();
        }



    }
    /**
     * @Description: 从目标数据源map集合中查找是否存在指定名称的数据源
     * @Author zhangyu
     */
    public  DruidDataSource findDataSource(String dataSourceName) {
        defineTargetDataSources = dynamicDataSource.getDefineTargetDataSources();
        if(defineTargetDataSources.containsKey(dataSourceName)){
            return (DruidDataSource)defineTargetDataSources.get(dataSourceName);
        }
        return null;
    }
}
