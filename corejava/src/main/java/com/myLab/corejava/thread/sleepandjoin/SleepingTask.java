package com.myLab.corejava.thread.sleepandjoin;

import com.myLab.corejava.thread.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask extends LiftOff {
    public void run() {
        try {
            while(countDown-- > 0){
                System.out.println(status());
                //sleep方法可以使当前线程阻塞，线程控制器可以切换线程
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            //sleep()方法可能会抛出interruptedException，由于异常不可以跨线程传播回main，因此必须在本地处理所有内部产生的异常
            System.err.println("Interrupted");
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
            exec.execute(new SleepingTask());
        exec.shutdown();
    }
}
