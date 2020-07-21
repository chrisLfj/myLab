package com.myLab.corejava.thread.shareresource.synchronizedDemo;

/**
 * 演示synchronized关键字的锁范围，
 */
public class SafeShareUseSyncDemo2 implements Runnable{
    private static int count;

    public SafeShareUseSyncDemo2() {
        count = 0;
    }

    public void run() {
        if(Thread.currentThread().getName().contains("1"))
            synch();
        else if(Thread.currentThread().getName().contains("2"))
            synch1();
        else
            unSynch();
    }

    public synchronized void synch(){
        System.out.println(Thread.currentThread().getName() + "进入同步方法，开始休眠");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "同步方法执行结束");
    }

    public synchronized void synch1(){
        System.out.println(Thread.currentThread().getName() + "进入同步方法，开始休眠");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "同步方法执行结束");
    }

    public void unSynch(){
        System.out.println(Thread.currentThread().getName() + "进入非同步方法，开始休眠");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "非同步方法执行结束");
    }

    public static int getCount(){
        return count;
    }

    public static void main(String[] args) {
        SafeShareUseSyncDemo2 us = new SafeShareUseSyncDemo2();

        new Thread(us, "Thread" + 1).start();
        new Thread(us, "Thread" + 2).start();
        new Thread(us, "Thread" + 3).start();
    }
}
/*
从执行结果可以发现，线程1先进入了同步方法，它拿到了锁，但是这个锁并没有锁住整个对象，而只是锁住了所有加了synchronized修饰的方法。
因此，线程2必须要等待线程1释放synch方法的锁，才能获得synch1方法的锁，因为这两个方法都是synchronized修饰的。
而线程3则可以马上访问该对象的非同步方法，它不受锁的限制。

Thread1进入同步方法，开始休眠
Thread3进入非同步方法，开始休眠
Thread3非同步方法执行结束
Thread1同步方法执行结束
Thread2进入同步方法，开始休眠
Thread2同步方法执行结束
 */
