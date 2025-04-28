package com.application.dynamic;

import java.lang.annotation.*;

/**
 * @Description: TODO:自定义多数据源切换注解
 * 优先级：先方法，后类，如果方法覆盖了类上的数据源类型，以方法的为准，否则以类上的为准
 * @Author: yyalin
 * @CreateDate: 2023/7/17 14:00
 * @Version: V1.0
 */
@Target({ ElementType.METHOD , ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    public String value() default DbsConstant.master;
}
