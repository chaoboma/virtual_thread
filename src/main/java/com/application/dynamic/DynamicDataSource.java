package com.application.dynamic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description: TODO：动态数据源
 * @Author: yyalin
 * @CreateDate: 2023/7/16 14:46
 * @Version: V1.0
 */

/**
 *
 * 调用AddDefineDataSource组件的addDefineDynamicDataSource（）方法，获取原来targetdatasources的map，
 * 并将新的数据源信息添加到map中，并替换targetdatasources中的map
 * 切换数据源时可以使用@DataSource(value = "数据源名称")，或者DynamicDataSourceContextHolder.setContextKey("数据源名称")
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicDataSource extends AbstractRoutingDataSource {
    //备份所有数据源信息，
    private Map<Object, Object> defineTargetDataSources;

    /**
     * 决定当前线程使用哪个数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDynamicDataSourceKey();
    }




}
