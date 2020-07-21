package com.myLab.corejava.thread.shareresource.synchronizedDemo;

/**
 * 典型的很简单的一个共享变量冲突的例子。
 * 为什么会出现并发问题，让我们从计算机的工作原理来理解。对象创建之后，变量是存放在内存中的，那cpu要进行计算的时候，为了提升效率会先从
 * 内存中将变量的值读取到本地缓存中，进行计算之后，再写入内存中。在多线程情况下，首先“读操作”是不存在线程安全问题的，同一时刻，多个线程读到的
 * 内存值可能是一样的，然后各自进行计算之后再放回内存中时，就会存在并发冲突的问题。
 * 并发有三个典型问题要解决：
 * 1.原子性，并发领域中的原子性是指一组操作要么不执行，要么就不可中断的都执行，而实际上只有读操作和写内存的操作才是原子性的，
 * 如果程序执行能够满足原子性的要求就不会存在线程冲突问题，举个例子，对于i++这个操作来说，它实际上是i=i+1,变成机器指令之后，实际上是
 * 先从内存中将变量i的值读到本地缓存中，然后在操作栈中对其进行加1操作，最后再写回内存中。这一系列的操作，明显不具备原子性，在多线程场景下
 * 就会出现冲突。
 * 2.可见性，
 * 3.有序性，
 */
public class UnsafeShare implements Runnable{
    private static int count;

    public UnsafeShare() {
        count = 0;
    }

    public void run() {
        for(int i = 0; i < 20; i++){
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
        UnsafeShare us = new UnsafeShare();
        for(int i = 0; i < 5; i++){
            new Thread(us, "Thread" + i).start();
        }
        try {
            Thread.sleep(10000);
            System.out.println(UnsafeShare.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*
从结果来看，5个线程，每个线程都会执行5次count++，由于每次count++之后使用了sleep方法放弃了cpu，这时cpu会进行线程切换让其它线程执行，增加了并发度，
导致线程的执行是无序的，它不会执行完Thread0再执行Thread1，而是交错执行
同时还导致了执行结果不正确，出现了并发冲突
Thread0:0
Thread1:1
Thread2:2
Thread3:3
Thread4:4
Thread4:5
Thread3:6
Thread2:7
Thread1:8
Thread0:9
Thread1:10
Thread0:11
Thread2:12
Thread3:13
Thread4:14
Thread2:16
Thread0:17
Thread1:15
Thread3:18
Thread4:19
Thread1:20
Thread4:22
Thread3:23
Thread0:21
Thread2:20
 */
