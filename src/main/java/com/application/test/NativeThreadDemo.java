package com.application.test;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

public class NativeThreadDemo {
    public static void main(String[] args) {
        try{
            int THREAD_NUM = 5000;
            Instant start = Instant.now();
            CountDownLatch taskLatch = new CountDownLatch(THREAD_NUM);
            for (int i = 0;i< THREAD_NUM; i++) {
                //System.out.println("i = " + i);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                            System.out.println("native thread finish:"+Thread.currentThread());
                            taskLatch.countDown();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
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

