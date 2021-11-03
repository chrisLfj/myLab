package com.myLab.corejava.thread.shareresource.reentrantLockDemo;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo implements Runnable{
    private static int count;
    private static final ReentrantLock lock = new ReentrantLock();//将static去掉再试试
//    private final ReentrantLock lock = new ReentrantLock();
//    private static final ReentrantLock lock = new ReentrantLock(true);//创建可以实现公平锁的自旋锁


    public ReentrantLockDemo() {
        count = 0;
    }

    public void run() {
        lock.lock();
        for(int i = 0; i < 5; i++){
            System.out.println(Thread.currentThread().getName() + ":" + (count++));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();//一定要记住解锁，否则其它线程永远没有机会获得锁
    }

    public static int getCount(){
        return count;
    }

    public static void main(String[] args) {
        ReentrantLockDemo us1 = new ReentrantLockDemo();
        ReentrantLockDemo us2 = new ReentrantLockDemo();
        ReentrantLockDemo us3 = new ReentrantLockDemo();

        new Thread(us1, "Thread" + 1).start();
        new Thread(us2, "Thread" + 2).start();
        new Thread(us3, "Thread" + 3).start();
        try {
            Thread.sleep(15000);
            System.out.println(ReentrantLockDemo.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*
由于使用了static关键字修饰了ReentrantLock变量，因此这个变量对于所有对象来说是共享的，这个就等同于加了一把“类锁”，
意味着，只要线程1拿到了锁，就意味着其它线程即使持有的是不同的对象，也要等线程1的锁释放才可以有机会得到锁，因此得到下面的执行结果，可以看到结果是串行并且有序的
Thread2:0
Thread2:1
Thread2:2
Thread2:3
Thread2:4
Thread3:5
Thread3:6
Thread3:7
Thread3:8
Thread3:9
Thread1:10
Thread1:11
Thread1:12
Thread1:13
Thread1:14
15

如果将static关键字去掉，那么这个ReentrantLock就等同于一把“对象锁”，锁只在对象范围内有效，等同于synchronized(this),
因此执行效果如下，可以看到是无需且并发不安全的
Thread3:0
Thread1:1
Thread2:2
Thread3:3
Thread1:5
Thread2:4
Thread3:6
Thread1:7
Thread2:8
Thread3:9
Thread1:10
Thread2:11
Thread3:12
Thread1:12
Thread2:13
14
 */
