package com.myLab.corejava.thread.shareresource.synchronizedDemo;

/**
 * 使用synchronized关键字解决并发冲突问题
 */
public class SafeShareUseSync implements Runnable{
    private static int count;

    public SafeShareUseSync() {
        count = 0;
    }

    public synchronized void run() {//被
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for(int i = 0; i < 5; i++){
            System.out.println(Thread.currentThread().getName() + ":" + (count++));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getCount(){
        return count;
    }

    public static void main(String[] args) {
        SafeShareUseSync us = new SafeShareUseSync();
        for(int i = 0; i < 5; i++){
            new Thread(us, "Thread" + i).start();
        }
        try {
            Thread.sleep(15000);
            System.out.println(SafeShareUseSync.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*
使用了synchronized关键字给run方法加锁，synchronized修饰方法或者代码块的时候，如果多个线程都试图访问某一个对象中的synchronized方法，
那么同一时刻只能由一个线程访问执行对象的synchronized方法或者代码块，其它线程需要等待，即使它们访问的不是同一个synchronized方法或者代码块，也需要等待。
所以从下面的执行结果来看，线程是依次执行的，一个线程执行完之后后面的线程才会执行。
synchronized通过加对象锁的方式来解决并发的原子性，可见性，有序性问题，但是效率较低。
Thread0:0
Thread0:1
Thread0:2
Thread0:3
Thread0:4
Thread4:5
Thread4:6
Thread4:7
Thread4:8
Thread4:9
Thread3:10
Thread3:11
Thread3:12
Thread3:13
Thread3:14
Thread2:15
Thread2:16
Thread2:17
Thread2:18
Thread2:19
Thread1:20
Thread1:21
Thread1:22
Thread1:23
Thread1:24
25
 */
