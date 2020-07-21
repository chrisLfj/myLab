package com.myLab.corejava.thread.shareresource.volatileDemo;

/**
 * volatile无法避免并发冲突，volatile解决的是可见性问题，
 * 假如多个线程并发的对一个共享变量进行运算，每个线程都会先从内存中获取变量值到本地缓存(寄存器)，然后进行运算之后，再写入内存。
 * 假如线程1从内存中获取了值到本地，并且完成了计算，这时线程1干别的事情去了，或者让cpu挂起了，那它对于共享变量的计算结果迟迟不会刷新到内存中。
 * 意味着其它线程迟迟读不到线程1的计算结果，它们读到的还是旧值或者其它线程的计算结果。
 * 使用volatile修饰的共享变量，会在线程操作完成之后，立即强制将其刷新到内存中，并且将该变量在本地缓存中的值标记为无效，这样会有几个结果
 * 1.如果其它线程还没有去内存中读值，那它从内存中读到的就是最新值
 * 2.如果其它有些线程已经读取了内存中的旧值到本地缓存，只要还没有使用该缓存值，那么它在使用的时候会发现该缓存值已经失效了，会再次从内存中读一次。
 * 3.如果其它有些线程已经读取了内存中的旧值到本地缓存，并且已经使用了该缓存值，比如，已经将该缓存值复制到了操作栈中，即将进行运算。
 *   这时即使缓存值已经失效了，该线程也不会再次从内存中读取了，它将使用旧值进行运算，这样就出现并发冲突了。
 */
public class ShareUseVolatile implements Runnable{
    private static volatile int count;

    public ShareUseVolatile() {
        count = 0;
    }

    public void run() {
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
        ShareUseVolatile us = new ShareUseVolatile();
        for(int i = 0; i < 5; i++){
            new Thread(us, "Thread" + i).start();
        }
        try {
            Thread.sleep(15000);
            System.out.println(ShareUseVolatile.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*
volatile关键字无法避免并发冲突
Thread0:0
Thread1:1
Thread2:2
Thread3:3
Thread4:4
Thread0:5
Thread3:7
Thread1:5
Thread2:6
Thread4:8
Thread0:9
Thread1:11
Thread3:10
Thread2:12
Thread4:13
Thread2:14
Thread0:15
Thread3:16
Thread1:17
Thread4:18
Thread0:19
Thread2:20
Thread1:19
Thread3:19
Thread4:21
22
 */
