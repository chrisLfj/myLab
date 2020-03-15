package com.myLab.corejava.thread.terminatetask;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 下面的例子中有三种可以让线程blocked的代码分别是sleep，等待输入/输出，等待获得对象同步锁
 * 然后我们要尝试在外边将blocked的线程强制中断(使用Future的cancel方法)
 * 结论是，sleep可以被中断，而其它两种情况的线程无法被中断
 */
class SleepBlocked implements Runnable{
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);//因sleep一段时间而Blocked
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        System.out.println("Exiting SleepBlocked.run");
    }
}

class IOBlocked implements Runnable{
    private InputStream in;

    public IOBlocked(InputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        System.out.println("waiting for read():");
        try {
            in.read();//该线程因等待输入而Blocked
        } catch (IOException e) {
            if(Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted from block I/O");
            }else{
                throw new RuntimeException(e);
            }
        }
        System.out.println("Exiting IOBlocked.run()");
    }
}

class SynchronizedBlocked implements Runnable {

    public synchronized void f(){
        while(true)  //这个循环不可能跳出，也就意味着永远不会释放对象锁，其它线程无法访问该同步方法
            Thread.yield();
    }

    public SynchronizedBlocked() {
        new Thread(){
            public void run(){
                f();//占有对象同步锁
            }
        }.start();
    }

    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f();//会因等待对象同步锁释放而Blocked
        System.out.println("Exiting SynchronizedBlocked.run()");
    }
}
public class Interrupting {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static void test(Runnable r) throws InterruptedException {
        Future<?> f = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting " + r.getClass().getName());
        f.cancel(true);
        System.out.println("Interrupt sent to " + r.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);//因为IO和synchronized块上的等待是不可中断的，因此这里要做一个系统退出。
    }
}
