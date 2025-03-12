package com.application.test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

public class VirtualThreadFactoryDemo {
    public static void main(String[] args) {
        try{
            // 创建一个虚拟线程工厂
            var threadFactory = Thread.ofVirtual().factory();
            int THREAD_NUM = 5000;
            CountDownLatch taskLatch = new CountDownLatch(THREAD_NUM);
            Instant start = Instant.now();
            // 使用虚拟线程执行任务
            for (int i = 0; i < THREAD_NUM; i++) {
                var vt = threadFactory.newThread(() -> {
                    //模拟IO阻塞型任务
                    try {
                        Thread.sleep(100);
                        System.out.println("virtual thread finish:"+Thread.currentThread());
                        taskLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                vt.start();
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
