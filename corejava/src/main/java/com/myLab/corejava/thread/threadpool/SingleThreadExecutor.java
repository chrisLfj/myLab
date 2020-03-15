package com.myLab.corejava.thread.threadpool;

import com.myLab.corejava.thread.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {
    public static void main(String[] args) {
        //就像是容量为1的FixedThreadPool，只维护一个线程，提交给它的任务会排队，按照提交的顺序依次执行
        //比较适合长期存活的任务，比如监听进入的套接字连接任务
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 5; i++){
            exec.execute(new LiftOff());
        }
        //shutdown()方法的调用可以防止新任务被提交给这个Excutor
        exec.shutdown();
    }

}
