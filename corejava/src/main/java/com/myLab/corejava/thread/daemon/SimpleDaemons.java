package com.myLab.corejava.thread.daemon;

import java.util.concurrent.TimeUnit;

/**
 * 用来演示后台线程
 * 所谓后台线程，是指程序运行的时候在后台提供一种服务的线程，该线程由程序创建，但却不属于程序的不可或缺的一部分
 * 也就是说，当程序的所有非后台线程都结束时程序就终止了，同时会杀死进程中所有后台线程。
 * 反过来说，只要有任何非后台线程还在运行，程序就不会终止
 */
public class SimpleDaemons implements Runnable {
    @Override
    public void run() {
        try {
            while(true){
                //通过调整这个timeout，可以看出一些差别
                //如果在这里加上一段代码来启动其它线程，那这些线程也会自动变为后台线程
                TimeUnit.MILLISECONDS.sleep(1700);
                System.out.println(Thread.currentThread().getName() + " " + this);
            }
        } catch (InterruptedException e) {
            System.out.println("sleep() interrupted");
        }
    }

    public static void main(String[] args) throws Exception {
        for(int i = 0; i < 10; i++){
            Thread daemon = new Thread(new SimpleDaemons());
            //要通过setDaemon方法设置为后台线程
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("all daemons started");
        //主线程在创建完所有后台线程之后会睡一会，然后就结束，会杀死所有还在运行的后台线程
        TimeUnit.MILLISECONDS.sleep(175);
    }
}
