package com.myLab.corejava.thread.shareresource.atomic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对线程不安全的AtomicityTest进行改造，使用AtomictInteger来封装i，可以实现操作的原子性(所谓原子性操作，即操作过程中不被外界干扰)
 *
 */
public class NewAtomicityTest implements Runnable{
    private AtomicInteger i = new AtomicInteger(0);
    public int getValue(){ return i.get();}//读取操作保证可见性(对共享变量的操作)
    private void eventincrement(){i.addAndGet(2);}//原子性操作加2

    @Override
    public void run() {
        while(true)
            eventincrement();
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("Timeout Aborting");
                System.exit(0);
            }
        }, 5000);
        ExecutorService exec = Executors.newCachedThreadPool();
        NewAtomicityTest at = new NewAtomicityTest();
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
