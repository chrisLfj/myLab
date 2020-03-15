package com.myLab.corejava.thread.terminatetask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 该示例中，演示了对于一个运行中的线程是无法被interrupt()方法中断的，
 * interrupt方法会改变线程的interrupted标识，对于一个sleep，wait，blocked中的线程是有效的，会要求其立即捕获InterruptedException异常并进行处理
 * 该示例中尝试了几种方式，分别是Thread的interrupt()方法，ExecutorService的shutdownNow()方法，Future的cancel(true)方法，
 * 从运行结果上来看以上三种方式都更改了线程的interrupted标识，但是都无法阻止线程的运行
 */
class Counter implements Runnable{
    private int i = 10000;

    @Override
    public void run() {
        while(i-- > 0){
            System.out.println("the interrupt status is: " + Thread.interrupted());
            System.out.println("this is: " + Thread.currentThread().getName() + " " + i);
            Thread.yield();
        }
    }
}
public class Interrupting3 {
    public static void main(String[] args) throws InterruptedException {
        //-----------第一段示例代码开始----------
//        Thread t1 = new Thread(new Counter());
//        t1.start();
//        TimeUnit.MILLISECONDS.sleep(10);
//        System.out.println("try to interrupt t1");
//        t1.interrupt();
        //-----------第一段示例代码结束----------

        //-----------第二段示例代码开始----------
//        ExecutorService exec = Executors.newCachedThreadPool();
//        exec.execute(new Counter());
//        TimeUnit.MILLISECONDS.sleep(10);
//        System.out.println("try to interrupt ");
//        exec.shutdownNow();
        //-----------第二段示例代码结束----------

        //-----------第三段示例代码开始----------
//        ExecutorService exec = Executors.newCachedThreadPool();
//        Future<?> f = exec.submit(new Counter());
//        TimeUnit.MILLISECONDS.sleep(10);
//        System.out.println("try to interrupt ");
//        f.cancel(true);
        //-----------第三段示例代码结束----------
    }
}
/*
第一段示例代码output:
the interrupt status is: false
this is: Thread-0 999
the interrupt status is: false
try to interrupt t1
this is: Thread-0 998
the interrupt status is: true
this is: Thread-0 997
the interrupt status is: false
...
一直执行完毕

第二段示例代码output:
the interrupt status is: false
this is: pool-1-thread-1 969
the interrupt status is: false
this is: pool-1-thread-1 968
try to interrupt
the interrupt status is: true
this is: pool-1-thread-1 967
...
一直执行完毕

第三段示例代码output:
the interrupt status is: false
this is: pool-1-thread-1 958
the interrupt status is: false
this is: pool-1-thread-1 957
try to interrupt
the interrupt status is: true
this is: pool-1-thread-1 956
...
一直执行完毕
 */
