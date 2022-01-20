package com.myLab.corejava.thread.terminatetask;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在Interrupting示例中已经展示了，对象的同步锁如果出现互斥阻塞时无法被中断，这就会产生一些隐秘又严重的问题
 * SE5并发类库中添加了一个特性，即使用ReentranLock类来加锁，并且它具备可以被中断的能力
 * ReentranLock类有三个主要的获取锁的方法如下：
 * 1）lock(), 拿不到lock就不罢休，不然线程就一直block。
 * 2）tryLock()，马上返回，拿到lock就返回true，不然返回false。它可以带时间限制，拿不到lock，就等一段时间，超时返回false。
 * 3）lockInterruptibly()，拿到锁就返回，如果拿不到则block，那可以通过调用该线程的interrupt()方法设置中断标志，线程会被唤醒并被要求处理InterruptedException。
 */
class BlockedMutex{
    private Lock lock = new ReentrantLock();

    public BlockedMutex() {
        lock.lock();//获得锁
    }

    public void f(){
        try {
            System.out.println("try to acquire lock in f()");
            lock.lockInterruptibly();//其它任务访问到这里尝试获得锁，并阻塞在这里，是因为在构造函数中就获得了锁并且一直没有释放
        } catch (InterruptedException e) {
            System.out.println("interrupted from lock acquisition in f()");
        }
    }
}

class Blocked2 implements Runnable{
    BlockedMutex blocked = new BlockedMutex();
    @Override
    public void run() {
        System.out.println("waiting for f() in BlockedMutex");
        blocked.f();
        System.out.println("Broken out of blocked call");
    }
}
public class Interrupting2 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Blocked2());//这里main线程实例化Blocked2，它会初始化其blocked属性，进而获得锁
        t.start();//另起一个线程，运行run方法时会尝试去获得blocked的锁，引起阻塞
        TimeUnit.SECONDS.sleep(10);
        System.out.println("Issuing t.interrupt()");
        t.interrupt();
    }
}
/*
output:
waiting for f() in BlockedMutex
try to acquire lock in f()
Issuing t.interrupt()
interrupted from lock acquisition in f()
Broken out of blocked call
 */
