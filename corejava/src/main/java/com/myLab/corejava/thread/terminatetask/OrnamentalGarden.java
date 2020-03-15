package com.myLab.corejava.thread.terminatetask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 程序说明：这里使用Count对象来跟踪花园参观者的主计数值，并且将其当作Entrance类中的一个静态域进行存储。
 * Count的increment和value方法都是synchronized的，用来控制对count域的访问。如果将increment上的synchronized
 * 关键字注释掉，那么这个程序就会崩溃，因为多个任务将同时访问并修改count(yield方法会使问题更快的发生)
 * 每个Entrance任务都维护着一个本地值number，它包含通过某个特定入口进入的参观者数量。这提供了对count对象的双重检查，
 * 以确保其记录的参观者数量使正确的。
 * 因为Entrance.canceled是一个volatile布尔标志，而它只会被读取和赋值(不会与其他域组合在一起被读取)，所以不需要同步对其的访问就可以安全操作它
 * 3秒之后，main向Entrance发送cancel()消息，然后调用exec对象的shutdown方法，之后调用exec上的awaitTermination等待每个任务结束，如果所有任务
 * 在超时时间达到之前全部结束，则返回true，否则返回false，表示不是所有的任务都已经结束了。
 */
class Count {
    private int count = 0;
    private Random rand = new Random(47);

    public synchronized int increment() {
        int temp = count;
        if(rand.nextBoolean())
            Thread.yield();
        return (count = ++temp);
    }
    public synchronized int value(){return count;}

}

class Entrance implements Runnable {
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<Entrance>();
    private int number = 0;
    private final int id;
    private static volatile boolean canceled = false;

    public static void cancel(){canceled = true;}

    public Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    @Override
    public void run() {
        while(!canceled){
            synchronized (this){
                ++number;
            }
            System.out.println(this + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("sleep interrupted");
            }
        }
        System.out.println("Stopping " + this);
    }

    public synchronized int getValue() {return number;}
    public String toString(){
        return "Entrance " + id + ": " + getValue();
    }
    public static int getTotalCount() {
        return count.value();
    }
    public static int sumEntrances() {
        int sum = 0;
        for(Entrance entrance: entrances)
            sum += entrance.getValue();
        return sum;
    }
}
public class OrnamentalGarden {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++)
            exec.execute(new Entrance(i));
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();
        if(!exec.awaitTermination(250, TimeUnit.MICROSECONDS))
            System.out.println("Some tasks were not terminated!");
        System.out.println("Total: " + Entrance.getTotalCount());
        System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
    }
}
