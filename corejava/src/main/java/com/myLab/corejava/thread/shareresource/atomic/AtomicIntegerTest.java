package com.myLab.corejava.thread.shareresource.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest implements Runnable{
    private static AtomicInteger atomicInteger = new AtomicInteger(100);
    private static int id = 0;

    @Override
    public void run() {
        System.out.println(atomicInteger.decrementAndGet());
//        System.out.println(atomicInteger.incrementAndGet());
//        id = id + 1;
//        System.out.println(++id);
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 100; i++){
            AtomicIntegerTest at = new AtomicIntegerTest();
            exec.execute(at);
        }
    }
}
