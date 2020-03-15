package com.myLab.corejava.thread.shareresource.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这个例子原本的逻辑是AtomicityTest的同步方法每次会对i进行原子性操作(两次加1)使其每次运算结果都是偶数，
 * synchronized关键字可以保证方法体内的代码在运行时不被其它任务干扰，因为当synchronized方法被一个任务调用后，对象就会加锁，该对象内的所有synchronized方法都不可以被其它任务调用
 * 一直等到当前任务调用推出后才会释放锁
 * 但通过public方法供外界调用访问i，这个就会有问题，因为该方法没有加锁，任何任务都可以调用，并且i又不具备对其它任务的可见性，因此当其它任务在i的两次加一过程中调用了该方法，那就会得到一个奇数。
 * 如果想让getValue每次获得的是偶数，将eventincrement方法的逻辑改成i+=2即可，但这并不能保证解决并发访问共享资源的问题，即每次getValue有可能获得的是相同的偶数
 * 如果想让getValue每次获得的是不同的偶数，则需要将getValue方法也加上synchrionized关键字修饰
 */
public class AtomicityTest implements Runnable{
    private int i = 0;
    public int getValue(){ return i;}
    private synchronized void eventincrement(){i++; i++;}//之所以连续两次加1，是为了验证

    @Override
    public void run() {
        while(true)
            eventincrement();
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest at = new AtomicityTest();
        exec.execute(at);
        while(true){
            int val = at.getValue();
            if(val%2 != 0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
