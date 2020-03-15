package com.myLab.corejava.thread.handleexception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 如何对从thread逃逸出来的异常进行捕获，可以通过自定义异常处理类，加入到线程实例中，负责处理逃逸出来的异常
 */
class ExceptionThread2 implements Runnable{

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run() by " + t);
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException("抛出异常");
    }
}

/**
 * 自定义线程异常处理类
 */
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught " + e);
    }
}

/**
 * 自定义Thread工厂类，它负责创建线程
 */
class HandlerThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        System.out.println("created " + t);
        //创建线程时加入自定义的异常处理类
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return t;
    }
}

public class CaptureUncaughtException {
    public static void main(String[] args) {
        //线程池执行器将使用自定义的线程工厂实例来创建线程
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
    }
}
