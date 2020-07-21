package com.myLab.corejava.thread.shareresource.synchronizedDemo;

/**
 * 演示synchronized关键字的锁范围，
 */
public class SafeShareUseSyncDemo1 implements Runnable{
    private static int count;

    public SafeShareUseSyncDemo1() {
        count = 0;
    }

    public synchronized void run() {
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
        SafeShareUseSyncDemo1 us1 = new SafeShareUseSyncDemo1();
        SafeShareUseSyncDemo1 us2 = new SafeShareUseSyncDemo1();
        SafeShareUseSyncDemo1 us3 = new SafeShareUseSyncDemo1();

        new Thread(us1, "Thread" + 1).start();
        new Thread(us2, "Thread" + 2).start();
        new Thread(us3, "Thread" + 3).start();
        try {
            Thread.sleep(15000);
            System.out.println(SafeShareUseSyncDemo1.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*
从执行结果可以发现，线程又再并发同时执行，执行无序，最终结果是14，说明出现了并发冲突问题，虽然run方法使用了synchronized关键字修饰，但是锁的范围是对象。
也就是说多个线程同时在访问一个对象的synchronized方法时，那同一时刻只能有一个线程取到锁来执行，其它线程等待，这些线程之间是互斥状态，线程是安全的。
但如果像上面main方法中，创建了三个对象us1，us2，us3，然后分别由三个线程来执行，这时每个线程独享对象锁，然后同时去操作静态变量count，又会出现并发问题了。
因此，如果synchronized的锁粒度是对象级别的，那在使用时要注意该对象最好是单例的，否则还是有可能出现并发冲突的问题。
Thread1:1
Thread2:0
Thread3:2
Thread1:3
Thread2:4
Thread3:5
Thread1:6
Thread3:7
Thread2:8
Thread1:9
Thread2:10
Thread3:11
Thread1:12
Thread2:13
Thread3:13
14
 */
