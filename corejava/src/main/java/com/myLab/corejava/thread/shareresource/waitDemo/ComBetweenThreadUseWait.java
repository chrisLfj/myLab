package com.myLab.corejava.thread.shareresource.waitDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 如果线程1在获得了对象锁之后，需要等到线程2的执行结果才可以继续执行。
 * 理想的办法是，由线程2通知线程1继续执行，这样的情况被称为线程间通信。
 * wait/notify/notifyAll这三个方法就是为此而设计的
 */
public class ComBetweenThreadUseWait implements Runnable{

    private static final byte[] flag = new byte[0];
    @Override
    public void run() {
        synchronized (flag){
            try {
                System.out.println(Thread.currentThread().getName() + " before flag.wait()");
//                flag.wait();//调用wait方法之后，处于waiting状态，当被唤醒后回到blocked状态继续抢锁，如果抢到了锁，则会进入runnable状态等待获取CPU时间片，获得了CPU时间片之后，继续执行下面的代码，之前的代码不会重复执行。
                flag.wait(5000);//调用wait(...)方法后，会处于timed_waiting超时等待的状态，超时之后就不再等待，会继续运行
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
        es.execute(new ComBetweenThreadUseWait());
        es.execute(new ComBetweenThreadUseWait());
        es.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (flag) {
                    //这里要注意，这里必须要使用synchronized，wait/notify/notifyAll这三个方法都是基于synchronized关键字来设计的，它们只能在由synchronized修饰的同步方法或者同步块里面使用
                    //否则会抛出异常。并且要唤醒的线程必须也要在同一个临界区中处于wait才行
//                    flag.notify();
                    flag.notifyAll();
                    System.out.println("send notify signal");
                }
            }
        });
        es.shutdown();
    }
}

/*
程序起了两个线程，先后获得flag锁，由于他们调用了wait()方法，随后他们一直处于waiting状态(放弃锁和cpu)，直到被中断或者唤醒才会继续参与运行
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

3.如果调用的是wait(超时时间)，随后会处于timed_waiting状态(超时等待)
 */
