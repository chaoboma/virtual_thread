package com.application.dynamic;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: TODO： 数据源信息配置类，读取数据源配置信息并注册成bean。
 * @Author: yyalin
 * @CreateDate: 2023/7/16 14:54
 * @Version: V1.0
 */
@Configuration
@MapperScan("com.application.mapper")
@Slf4j
public class DynamicDataSourceConfig {
    @Bean(name = DbsConstant.master)
    @ConfigurationProperties("spring.datasource.dynamic.datasource.master")
    public DataSource masterDataSource() {
        log.info("数据源切换为：{}",DbsConstant.master);
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = DbsConstant.slave)
    @ConfigurationProperties("spring.datasource.dynamic.datasource.slave")
    public DataSource slaveDataSource() {
        log.info("数据源切换为：{}",DbsConstant.slave);
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }



    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(){
        ConcurrentHashMap<Object, Object> dataSourceMap = new ConcurrentHashMap<>(3);
        dataSourceMap.put(DbsConstant.master,masterDataSource());
        dataSourceMap.put(DbsConstant.slave,slaveDataSource());
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        //将数据源信息备份在defineTargetDataSources中
        dynamicDataSource.setDefineTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

}
