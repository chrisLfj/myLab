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
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
        System.out.println(getName() + " join completed");
    }
}

public class Joining {
    public static void main(String[] args) {
        Sleeper sleepy = new Sleeper("sleepy", 1500),
                grumpy = new Sleeper("grumpy", 1500);
        Joiner dopey = new Joiner("dopey", sleepy),
                doc = new Joiner("doc", grumpy);
        grumpy.interrupt();//sleeper被中断或正常结束时，joiner将一同结束
    }
}
