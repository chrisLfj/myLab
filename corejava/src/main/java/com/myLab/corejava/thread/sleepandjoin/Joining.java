package com.myLab.corejava.thread.sleepandjoin;

/**
 * 演示join以及sleep和interrupt
 * 所谓的join，是指在源线程中调用目标线程的join方法，这时源线程将被挂起，直到目标线程结束，源线程才恢复
 * sleep和interrupt，是指当一个线程处于sleep中时，它是可以被中断的，interrupt()方法被调用时，将给该线程设定一个标志，表明该线程已经被中断
 * 然而异常被捕获时将清理这个标志。
 */
class Sleeper extends Thread{
    private int duration;
    public Sleeper(String name, int sleepTime) {
        super(name);
        duration = sleepTime;
        start();
    }

    public void run(){
        System.out.println(getName() + " started");
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted. " + "isInterrutped:" + isInterrupted());
        }
        System.out.println(getName() + " is awakened");
    }
}

class Joiner extends Thread{
    private Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    public void run(){
        System.out.println(getName() + " started");
        try {
            sleeper.join();
//            sleeper.join(30000);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
        System.out.println(getName() + " join completed");
    }
}

public class Joining {
    public static void main(String[] args) {
//        Sleeper sleepy = new Sleeper("sleepy", 15000),
//                grumpy = new Sleeper("grumpy", 15000);
//        Joiner dopey = new Joiner("dopey", sleepy),
//                doc = new Joiner("doc", grumpy);
//        grumpy.interrupt();//sleeper被中断或正常结束时，joiner将一同结束
        Sleeper sleepey = new Sleeper("sleepy", 30000);
        Joiner dopey = new Joiner("dopey", sleepey);
    }
}

/*
1.sleep()会让线程放弃CPU，不会让线程放弃锁，它会让线程进入TIMED_WAITING状态
2.dopey线程调用sleepy.join()后，dopey线程处于WAITING状态，它会等待sleepy线程结束，而sleepy线程由于调用了sleep()方法，处于TIMED_WAITING状态
3.如果dopey线程调用sleepy.join(…)方法，dopey会进入TIMED_WAITING状态，它会在超时时间内等待sleepy线程结束，如果超时了sleepy线程还未结束，dopey不会继续等待，它会继续运行
 */
