package com.application.test;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

public class ThreadPoolTaskExecutorDemo {
    public static void main(String[] args) {
        try{
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(50000);
            executor.setMaxPoolSize(100000);
            executor.setQueueCapacity(100000);
            executor.setThreadNamePrefix("Async-");
            executor.initialize();

            int THREAD_NUM = 50000;
            Instant start = Instant.now();
            CountDownLatch taskLatch = new CountDownLatch(THREAD_NUM);
            for (int i = 0; i < THREAD_NUM; i++) {
                executor.execute(() -> {
                    // 模拟耗时任务
                    try {
                        Thread.sleep(1000);
                        System.out.println("virtual thread finish:"+Thread.currentThread());
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
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
