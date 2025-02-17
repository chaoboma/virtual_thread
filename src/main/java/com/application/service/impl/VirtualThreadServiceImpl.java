package com.application.service.impl;

import com.application.service.VirtualThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;


@Component
@Service
public class VirtualThreadServiceImpl implements VirtualThreadService {

    // 获取虚拟线程执行器
    @Autowired
    private ExecutorService virtualThreadExecutor;

    // 获取异步任务执行器
    @Autowired
    private ThreadPoolTaskExecutor asyncTaskExecutor;

    /**
     * 创建线程数量
     */
    private static Integer THREAD_NUM = 1000;

    /**
     * @throws InterruptedException
     */
    @Async("virtualThreadExecutor")
    public void testVirtualThreadTask() throws InterruptedException {
        Instant start = Instant.now();
        CountDownLatch taskLatch = new CountDownLatch(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            virtualThreadExecutor.execute(() -> {
                // 模拟耗时任务
                try {
                    Thread.sleep(1000);
                    System.out.println("virtual thread finish!");
                    taskLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        taskLatch.await();
        Instant end = Instant.now();
        long duration = Duration.between(start, end).toMillis();
        System.out.println("duration:" + duration + " ms");
    }

    @Async("asyncTaskExecutor")
    public void testNormalThreadTask() throws InterruptedException {
        Instant start = Instant.now();
        CountDownLatch taskLatch = new CountDownLatch(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            asyncTaskExecutor.execute(() -> {
                // 模拟耗时任务
                try {
                    Thread.sleep(1000);
                    System.out.println("normal thread finish!");
                    taskLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        taskLatch.await();
        Instant end = Instant.now();
        long duration = Duration.between(start, end).toMillis();
        System.out.println("duration:" + duration + " ms");
    }
}

