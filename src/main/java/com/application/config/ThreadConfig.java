package com.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class ThreadConfig {
    /**
     * 一个 Java普通线程对应一个操作系统线程，这些线程非常消耗资源。
     * 普通线程池定义：16个核心线程(模拟IO密集型任务，每个任务休眠1s)，最大线程数50，阻塞队列1000：避免触发拒绝策略
     * @return
     */
    @Bean(name = "asyncTaskExecutor")
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    /**
     * 虚拟线程是 Java 中最重要的创新之一。 它们是在 Project Loom 中开发的，自 Java 19 作为预览功能以来一直包含在 JDK 中，
     * 自 Java 21 作为最终版本 (JEP 444) 以来，它们已包含在 JDK 中。
     * @return
     */
    @Bean(name = "virtualThreadExecutor")
    public ExecutorService virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
