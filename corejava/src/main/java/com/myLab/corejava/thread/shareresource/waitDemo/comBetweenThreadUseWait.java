package com.myLab.corejava.thread.shareresource.waitDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 如果线程1在获得了对象锁之后，需要等到线程2的执行结果才可以继续执行。
 * 理想的办法是，由线程2通知线程1继续执行，这样的情况被称为线程间通信。
 * wait/notify/notifyAll这三个方法就是为此而设计的
 */
public class comBetweenThreadUseWait implements Runnable{

    private static final byte[] flag = new byte[0];
    @Override
    public void run() {
        synchronized (flag){
            try {
                System.out.println(Thread.currentThread().getName() + " before flag.wait()");
                flag.wait();//调用wait方法之后，处于block状态，当被唤醒后回到runnable状态继续抢锁，如果抢到了锁，则继续执行下面的代码，之前的代码不会重复执行。
                System.out.println(Thread.currentThread().getName() + " after flag.wait()");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " after sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new comBetweenThreadUseWait());
        es.execute(new comBetweenThreadUseWait());
        es.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (flag) {
                    //这里要注意，这里必须要使用synchronized，wait/notify/notifyAll这三个方法都是基于synchronized关键字来设计的，它们只能在由synchronized修饰的同步方法或者同步块里面使用
                    //否则会抛出异常。并且要唤醒的线程必须也要在同一个临界区中处于wait才行
//                    flag.notify();
                    flag.notifyAll();
                }
            }
        });
        es.shutdown();
    }
}

/*
程序起了两个线程，先后获得flag锁，并且处于wait状态(放弃锁和cpu处于block状态)
1.使用notify()方法时，只会随机唤醒一个等待线程，如下的结果，只唤醒了thread-1继续执行，而thread-2还是继续wait
pool-1-thread-1 before flag.wait()
pool-1-thread-2 before flag.wait()
pool-1-thread-1 after flag.wait()
pool-1-thread-1 after sleep

2.使用notifyAll()方法时，则会唤醒所有wait的线程，它们会再次回到runnable状态然后竞争锁并且继续执行。
pool-1-thread-2 before flag.wait()
pool-1-thread-1 before flag.wait()
pool-1-thread-1 after flag.wait()
pool-1-thread-1 after sleep
pool-1-thread-2 after flag.wait()
pool-1-thread-2 after sleep
 */
