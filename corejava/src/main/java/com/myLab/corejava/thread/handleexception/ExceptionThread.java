package com.myLab.corejava.thread.handleexception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程中的异常如果throw到外面，这种异常是无法在外面捕捉到的，它会一直传播到控制台
 */
public class ExceptionThread implements Runnable {
    @Override
    public void run() {
        //总是会抛出异常，在线程中没有处理直接throw出去
        throw new RuntimeException("线程异常");
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        //即使加了try...catch代码块试图捕捉线程逃逸出来的异常，也是无效的，无法捕捉它
        try {
            exec.execute(new ExceptionThread());
        }catch(RuntimeException e){
            System.out.println("Exception has been handled");
        }
    }
}
