package com.application.dynamic;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: TODO:数据源切换处理
 * DynamicDataSourceHolder类主要是设置当前线程的数据源名称，
 * 移除数据源名称，以及获取当前数据源的名称，便于动态切换
 * @Author: yyalin
 * @CreateDate: 2023/7/16 14:51
 * @Version: V1.0
 */
@Slf4j
public class DynamicDataSourceHolder {
    /**
     * 保存动态数据源名称
     */
    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_KEY = new ThreadLocal<>();

    /**
     * 设置/切换数据源，决定当前线程使用哪个数据源
     */
    public static void setDynamicDataSourceKey(String key){
        log.debug("数据源切换为：{}",key);
        DYNAMIC_DATASOURCE_KEY.set(key);
    }

    /**
     * 获取动态数据源名称，默认使用mater数据源
     */
    public static String getDynamicDataSourceKey(){
        String key = DYNAMIC_DATASOURCE_KEY.get();
        return key == null ? DbsConstant.select : key;
    }

    /**
     * 移除当前数据源
     */
    public static void removeDynamicDataSourceKey(){
        log.debug("移除数据源：{}",DYNAMIC_DATASOURCE_KEY.get());
        DYNAMIC_DATASOURCE_KEY.remove();
    }

}
