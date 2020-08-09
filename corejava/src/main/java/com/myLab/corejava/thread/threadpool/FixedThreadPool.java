package com.myLab.corejava.thread.threadpool;

import com.myLab.corejava.thread.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {
    public static void main(String[] args) {
        //一次性预先执行线程分配，FixedThreadPool使用的线程数是有界的，它会尽快的将线程都创建起来，准备好执行任务
        //当提交的任务数量大于FixedThreadPool的容量时，多余的任务会进入队列中等待空闲线程运行
        ExecutorService exec = Executors.newFixedThreadPool(4);
        for(int i = 0; i < 5; i++){
            exec.execute(new LiftOff());
//            exec.submit()
        }
        //shutdown()方法的调用可以防止新任务被提交给这个Excutor
        exec.shutdown();
    }

}
