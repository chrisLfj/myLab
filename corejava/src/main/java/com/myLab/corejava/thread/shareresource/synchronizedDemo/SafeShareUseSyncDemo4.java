package com.myLab.corejava.thread.shareresource.synchronizedDemo;

/**
 * 演示synchronized关键字的锁范围，
 */
public class SafeShareUseSyncDemo4 implements Runnable{
    private static int count;

    public SafeShareUseSyncDemo4() {
        count = 0;
    }

    public void run() {
        if(Thread.currentThread().getName().contains("3"))
            unSynch();
        else
            synch();
    }

    public void synch(){
        synchronized(SafeShareUseSyncDemo4.class){
            System.out.println(Thread.currentThread().getName() + "进入同步方法，开始休眠");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "同步方法执行结束");
        }
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

    public static void main(String[] args) {
        SafeShareUseSyncDemo4 us1 = new SafeShareUseSyncDemo4();
        SafeShareUseSyncDemo4 us2 = new SafeShareUseSyncDemo4();
        SafeShareUseSyncDemo4 us3 = new SafeShareUseSyncDemo4();

        new Thread(us1, "Thread" + 1).start();
        new Thread(us2, "Thread" + 2).start();
        new Thread(us3, "Thread" + 3).start();
    }
}
/*
对synchronized修饰类的时候，锁的粒度是类，该类的所有对象都共享一把锁，也就是说同一时刻只能有一个线程持有该类的synchronized锁。
而synchronized(this)或者synchronized(obj)这种锁的粒度是对象，这就是本质区别。
因此，从执行结果可以发现，当Thread1获得了对象us1的锁时，Thread2是无法获得对象us2的锁的，它必须等待，
但是Thread3却依然可以访问对象us1的非同步的方法，它并没有受到锁的限制。
对于类上加锁，粒度太大，并发度比较低
另外，由synchronized修饰静态方法时，与修饰类时的效果类似

Thread1进入同步方法，开始休眠
Thread3进入非同步方法，开始休眠
Thread3非同步方法执行结束
Thread1同步方法执行结束
Thread2进入同步方法，开始休眠
Thread2同步方法执行结束
 */
