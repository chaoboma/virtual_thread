package com.application.dynamic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: TODO:动态新增数据源信息
 * @Author: yyalin
 * @CreateDate: 2023/7/16 15:24
 * @Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceInfo {
    private String url;
    private String userName;
    private String password;
    private String datasourceKey;
    private String driverClassName;
}
