package com.myLab.corejava.thread.shareresource.atomic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * synchronized可以防止一个方法也可以一段代码块被同时访问
 * 被synchronized控制的代码块叫同步控制块，在进入此段代码之前，必须得到该对象实例的锁
 * 使用同步控制块，而不是对整个方法进行同步控制，可以使多个任务访问对象的时间性能得到显著提高
 * 下面这个例子比较两种同步控制方法的性能
 */
class Pair{//Pair类是线程不安全的
    private int x,y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {
        this(0,0);
    }

    public int getX(){ return x;}
    public int getY(){ return y;}
    public void incrementX(){ x++;}
    public void incrementY(){ y++;}
    public String toString(){
        return "x: " + x + ", y: " + y;
    }
    public class PairValuesNotEqualsException extends RuntimeException{
        public PairValuesNotEqualsException() {
            super("Pari values not equals: " + Pair.this);//Pair.this代表Pair类的实例引用
        }
    }
    public void checkState(){
        if(x != y)
            throw new PairValuesNotEqualsException();
    }
}

/**
 * 通过PairManager来访问Pair对象，它提供了很多线程安全的方法
 */
abstract class PairManager{
    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair p = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());//定义一个线程安全的集合
    public synchronized Pair getPair(){
        return new Pair(p.getX(), p.getY());
    }
    protected void store(Pair p){//由于storage本来就是线程安全的，因此这个方法不需要用synchronized控制也可以
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);//睡一会让别的线程可以工作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public abstract void increment();//定义一个模板方法，让不同的继承类可以使用不同的逻辑来实现该方法
}

class PairManager1 extends PairManager{

    //使用synchronized来控制整个方法
    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}

class PairManager2 extends PairManager{

    @Override
    public void increment() {
        Pair temp;
        //使用synchronized来控制一部分代码块，这样就给其它线程更多的机会来使用increment方法了，可以并发访问的提高效率
        synchronized (this){
            p.incrementX();
            p.incrementY();
            temp = getPair();
        }
        store(temp);
    }
}

class PairManipulator implements Runnable{
    PairManager pm;

    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while(true)
            pm.increment();
    }
    public String toString(){
        return "Pair: " + pm.getPair() + " CheckCounter = " + pm.checkCounter.get();
    }
}

class PairChecker implements Runnable{
    PairManager pm;

    public PairChecker(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while(true){
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}
public class CriticalSection {
    static void testApproaches(PairManager pman1, PairManager pman2){
        ExecutorService exec = Executors.newCachedThreadPool();
        PairManipulator pm1 = new PairManipulator(pman1);
        PairManipulator pm2 = new PairManipulator(pman2);
        PairChecker pcheck1 = new PairChecker(pman1);
        PairChecker pcheck2 = new PairChecker(pman2);
        exec.execute(pm1);
        exec.execute(pm2);
        exec.execute(pcheck1);
        exec.execute(pcheck2);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("pm1: " + pm1 + "\npm2: " + pm2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager pman1 = new PairManager1();
        PairManager pman2 = new PairManager2();
        testApproaches(pman1,pman2);
    }
}
/*
PairManager1和PairManager2被创建用来分别访问各自的Pair对象
两个PairManipulator任务分别使用PairManager1和PairManager2，调用其increment()
两个PairChecker任务，则是用来跟踪对应的PairManager具备可以运行条件的频度
output sample:
pm1: Pair: x: 13, y: 13 CheckCounter = 35224
pm2: Pair: x: 14, y: 14 CheckCounter = 37027272
从输出结果来看，pm2对应的Pair的x y值被操作次数更多，并且pm2的可运行品读更高
控制代码块可以使对象不加锁的时间更长，使得其它线程能够更多的访问对象
 */

