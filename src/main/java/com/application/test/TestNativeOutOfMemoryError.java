package com.application.test;

import java.util.concurrent.CountDownLatch;

public class TestNativeOutOfMemoryError {

    public static void main(String[] args) {

        for (int i = 0;; i++) {
            System.out.println("i = " + i);
            new Thread(new HoldThread()).start();
        }
    }

}

class HoldThread extends Thread {
    //CountDownLatch cdl = new CountDownLatch(1);

    public HoldThread() {
        this.setDaemon(true);
    }

    public void run() {
        try {
            Thread.sleep(100);
            System.out.println("native thread finish:"+Thread.currentThread());
            //cdl.await();
        } catch (InterruptedException e) {
        }
    }
}
