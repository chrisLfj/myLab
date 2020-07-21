package com.myLab.corejava.thread.shareresource.synchronizedDemo;

/**
 * 演示synchronized关键字的锁范围，
 */
public class SafeShareUseSyncDemo3 implements Runnable{
    private static int count;

    public SafeShareUseSyncDemo3() {
        count = 0;
    }

    public void run() {
        if(Thread.currentThread().getName().contains("1"))
            synchronized (this){
                System.out.println(Thread.currentThread().getName() + "进入同步代码块，开始休眠");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "同步代码块执行结束");
            }
        else if(Thread.currentThread().getName().contains("2"))
            synchronized (this){
                System.out.println(Thread.currentThread().getName() + "进入同步代码块，开始休眠");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "同步代码块执行结束");
            }
        else{
            System.out.println(Thread.currentThread().getName() + "进入非同步代码块，开始休眠");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "非同步代码块执行结束");
        }
    }

    public static void main(String[] args) {
        SafeShareUseSyncDemo3 us = new SafeShareUseSyncDemo3();

        new Thread(us, "Thread" + 1).start();
        new Thread(us, "Thread" + 2).start();
        new Thread(us, "Thread" + 3).start();
    }
}
/*
从执行结果可以发现，与SafeShareUseSyncDemo2的执行结果类似，synchronized修饰方法或者代码块，表现得行为是相似的。
线程1先进入了同步代码块，它拿到了锁，但是这个锁并没有锁住整个对象，而只是锁住了所有加了synchronized修饰的代码块。
因此，线程2必须要等待线程1释放同步代码块的锁，才能执行。
而线程3则可以马上访问该对象的非同步代码块，它不受锁的限制。

Thread1进入同步代码块，开始休眠
Thread3进入非同步代码块，开始休眠
Thread3非同步代码块执行结束
Thread1同步代码块执行结束
Thread2进入同步代码块，开始休眠
Thread2同步代码块执行结束
 */
