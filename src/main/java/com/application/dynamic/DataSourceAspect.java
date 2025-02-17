package com.application.dynamic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Description: TODO:利用AOP切面实现数据源的动态切换
 * @Author: yyalin
 * @CreateDate: 2023/7/17 14:03
 * @Version: V1.0
 */
@Aspect
@Order(-1)  //保证该AOP在@Transactional之前执行
@Component
public class DataSourceAspect {
    // 设置DataSource注解的切点表达式
    @Pointcut("@annotation(DataSource)")
    public void dynamicDataSourcePointCut(){}

    //环绕通知
    @Around("dynamicDataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        String key = getDefineAnnotation(joinPoint).value();
        DynamicDataSourceHolder.setDynamicDataSourceKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceHolder.removeDynamicDataSourceKey();
        }
    }
    /**
     * 功能描述:先判断方法的注解，后判断类的注解，以方法的注解为准
     * @MethodName: getDefineAnnotation
     * @MethodParam: [joinPoint]
     * @Return: com.wonders.dynamic.DataSource
     * @Author: yyalin
     * @CreateDate: 2023/7/17 14:09
     */
    private DataSource getDefineAnnotation(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataSource dataSourceAnnotation = methodSignature.getMethod().getAnnotation(DataSource.class);
        if (Objects.nonNull(methodSignature)) {
            return dataSourceAnnotation;
        } else {
            Class<?> dsClass = joinPoint.getTarget().getClass();
            return dsClass.getAnnotation(DataSource.class);
        }
    }
}
