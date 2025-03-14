package com.application.test;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTaskExecutorDemo {
    public static void main(String[] args) {
        try{
            int THREAD_NUM = 5000;
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(4);
            executor.setMaxPoolSize(100);
            executor.setQueueCapacity(100000);
            executor.setThreadNamePrefix("Async-");
            executor.initialize();


            Instant start = Instant.now();
            CountDownLatch taskLatch = new CountDownLatch(THREAD_NUM);
            for (int i = 0; i < THREAD_NUM; i++) {
                executor.execute(() -> {
                    // 模拟IO阻塞型任务
                    try {
                        Thread.sleep(100);
                        System.out.println("common thread finish:"+Thread.currentThread());
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
            executor.shutdown();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
