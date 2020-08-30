package com.myLab.corejava.thread.shareresource.parkDemo;

import java.util.concurrent.locks.LockSupport;

/**
 * 本类演示LockSupport的park方法，其本质是调用了unsafe类的park方法
 * park意为“停车”之意，可以将当前线程挂起，unpark方法则是用来唤醒由park方法挂起的线程，让其继续执行
 * 同样都可以对线程进行类似的“挂起/唤醒”操作，park/unpark与wait/notify/notifyAll方法相比更加的灵活
 * 1.wait/notify/notifyAll方法执行的前提是线程都必须要获得同一把锁，在同一个临界区内调用才有效
 * 2.notify/notifyAll只能随机唤醒一个线程或者唤醒所有线程
 * 3.park/unpark则可以在任何时候将指定线程挂起/唤醒
 * park和unpark底层实现方式，其实是通过给与线程运行许可“permit”的方式来控制线程的挂起和唤醒的
 * 当调用park方法时，当前线程的运行permit被剥夺(通过unsafe类的park方法来实现)，它就会挂起，除非被中断或者超时，否则会一直挂起
 * 当调用unpark方法时，当前线程的运行permit会被置为1，这里注意的是，这个permit最多只能是1，也就是说连续调用多次unpark也只会增加一次permit
 * 我们实际使用的过程中一般是先调用park方法将线程挂起，然后在某个地方调用unpark方法将线程唤醒
 * 其实也可以先调用unpark方法提前将线程的运行许可置为1，这时即使对该线程调用了park方法，该线程也不会被挂起，它不会受影响，但是如果再次调用park方法，该线程就会挂起
 * 连续调用多次unpark，跟调用一次unpark效果一样，只会许可运行一次
 */
public class ParkDemo {
    public static Integer shareCount = 0;
    public static void main(String[] args) {
        Thread parkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (shareCount){
                    System.out.println("线程开始挂起");
                    LockSupport.park();//park方法会让线程进入waiting状态，除非其它线程将其唤醒，它放弃cpu但是不会放弃锁
                    System.out.println("线程被唤醒");
                }
            }
        });
        Thread otherThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (shareCount){
                    System.out.println("other thread get lock!");
                }
            }
        });
        Thread unparkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始唤醒线程");
                LockSupport.unpark(parkThread);
                System.out.println("结束唤醒");
            }
        });
        Thread timeParkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程开始限时挂起");
                LockSupport.parkNanos(10000000000L);//让线程失去运行许可，10秒之后恢复运行
                System.out.println("线程结束限时挂起");
            }
        });
        timeParkThread.start();

        //-----------------------------------
        // 下面代码段的执行过程为：
        // 1.parkThread先获得了shareCount的锁，然后parkThread线程因为park方法而进入了waiting状态，但是它并不会放弃shareCount的锁
        // 2.otherThread线程一直试图获得shareCount锁，但是由于该锁一直被parkThread线程持有，因此otherThread会进入blocked状态
        // 3.unparkThread线程唤醒parkThread线程使其继续执行，执行完成之后释放锁
        // 4.otherThread获得锁，然后执行
//        try {
//            parkThread.start();
//            Thread.sleep(1000);
//            otherThread.start();
//            Thread.sleep(10000);
//            unparkThread.start();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //-----------------------------------
    }
}
