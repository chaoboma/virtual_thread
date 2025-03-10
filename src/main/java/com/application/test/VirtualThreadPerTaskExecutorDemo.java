package com.application.test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadPerTaskExecutorDemo {
    public static void main(String[] args) {
        try{
            int THREAD_NUM = 10000;
            ExecutorService virtualThreadExecutor= Executors.newVirtualThreadPerTaskExecutor();
            Instant start = Instant.now();
            CountDownLatch taskLatch = new CountDownLatch(THREAD_NUM);
            for (int i = 0; i < THREAD_NUM; i++) {
                virtualThreadExecutor.execute(() -> {
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
