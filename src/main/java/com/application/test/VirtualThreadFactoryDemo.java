package com.application.test;

public class VirtualThreadFactoryDemo {
    public static void main(String[] args) {
        // 创建一个虚拟线程工厂
        var threadFactory = Thread.ofVirtual().factory();

        // 使用虚拟线程执行任务
        for (int i = 0; i < 10000; i++) {
            var vt = threadFactory.newThread(() -> {
                System.out.println("Hello from Virtual Thread: " + Thread.currentThread());
            });
            vt.start();
        }

        // 等待所有虚拟线程完成（实际应用中需考虑更优雅的同步机制）
        // 这里仅作演示，未加入等待逻辑
    }
}
