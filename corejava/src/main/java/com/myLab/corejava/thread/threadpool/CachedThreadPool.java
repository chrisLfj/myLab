package com.myLab.corejava.thread.threadpool;

import com.myLab.corejava.thread.LiftOff;

import java.util.concurrent.*;

public class CachedThreadPool  {
    public static void main(String[] args) {
        //获得一个线程池执行器，它会创建一个线程池，然后按需创建线程，当创建的线程空闲了，他们还会被重复使用
        //线程池会显著提高执行很多short-lived异步任务的效率
        //调用其execute方法，会重用池中可以线程，如果没有则新创建一个线程
        //如果线程超过6秒没有被使用，它会被终止并从池中删除
        //SE5和6对线程的创建和执行设计了很多的Executor，从而简化了并发编程
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++){
            exec.execute(new LiftOff());
        }
        //shutdown()方法的调用可以防止新任务被提交给这个Excutor
        exec.shutdown();
    }

}
