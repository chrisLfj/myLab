package com.myLab.corejava.thread.shareresource.countDownLatchDemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * latch:闭锁，门闩。想象有这样一个带锁的房间，只允许指定数量的人进房间干活，干完活就离开。
 * 房间外有人在等待房间里的人都干完活之后才会继续做自己的事情。
 * CountDownLatch是一个并发工具类，典型的应用场景是，有一个任务想要往下执行，但必须要等到其它额任务执行完毕
 * 才可以继续往下执行。假如我们这个想要继续往下执行的任务调用一个CountDownLatch对象的await()方法，
 * 其他的任务执行完自己的任务后调用同一个CountDownLatch对象上的countDown()方法，此时cnt计数器的值就会减1。
 * 这个调用await()方法的任务将一直阻塞等待，直到这个CountDownLatch对象的计数值减到0为止。
 */
public class CountDownLatchDemo {

    static class Worker implements Runnable{

        private CountDownLatch countDownLatch;
        private String name;

        public Worker(CountDownLatch countDownLatch, String name) {
            this.countDownLatch = countDownLatch;
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(this.name + "正在干活");
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.name + "干完活了");
            countDownLatch.countDown();
        }
    }

    static class Boss implements Runnable{

        private CountDownLatch countDownLatch;

        public Boss(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("老板正在等所有的工人干完活");
            try {
                this.countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("工人都干完活了，老板开始检查了");
        }
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        Thread worker1 = new Thread(new CountDownLatchDemo.Worker(countDownLatch, "worker1"));
        Thread worker2 = new Thread(new CountDownLatchDemo.Worker(countDownLatch, "worker2"));
        Thread worker3 = new Thread(new CountDownLatchDemo.Worker(countDownLatch, "worker3"));
        Thread worker4 = new Thread(new CountDownLatchDemo.Worker(countDownLatch, "worker4"));
        Thread worker5 = new Thread(new CountDownLatchDemo.Worker(countDownLatch, "worker5"));
        Thread boss = new Thread(new CountDownLatchDemo.Boss(countDownLatch));
        worker1.start();
        worker2.start();
        worker3.start();
        worker4.start();
        worker5.start();
        boss.start();
    }
}
/*
CountDownLatch整个的实现用到了双向链表实现的一个阻塞队列来存放阻塞的线程，线程调用await方法的时候，会判断当前count是否等于0，如果已经等于0了，
那就不需要阻塞了，如果还不等于0那就阻塞并加入到队列中，使用了Unsafe类的park以及unpark方法来实现的线程阻塞和释放，但是这里要搞明白一个问题，
就是count这个值是如何实现线程安全的?比如线程调用await方法，其中获取count的时候，count=1，所以线程要进行阻塞，但是如果还没有完成阻塞的时候，
执行线程都执行完了，count被减一变成了0，这时之前的线程还没加入到阻塞队列中，这样是不是有可能出现刚才的线程一直阻塞无法被释放的情况？
使用了Unsafe类的cas方法来实现countdown减一的线程安全，当每次countdown后会检查一下当前count是否为0，如果为0则将队列中存放的阻塞线程
逐一释放(unpark方法)
 */
