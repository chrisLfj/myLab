package com.myLab.corejava.thread.terminatetask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShutDownDemo {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++){
            es.execute(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    System.out.println(Thread.currentThread().getName() + "开始");
                    //shutdown()无法终止这段程序，因为它不会调用正在运行的线程的interrupt方法，shutdown方法只能等待线程正常运行结束或者异常终止，
                    //并且拒绝再添加新的任务，但还是会将排队的任务执行完。
                    //shutdownNow()则可以有效终止这段程序，因为它会调用正在运行的线程的interrupt方法，
                    //并且拒绝再添加新的任务，同时还会马上清除正在排队的任务。
                    while(!Thread.currentThread().isInterrupted() && i < 100){
                        System.out.println(Thread.currentThread().getName() + " isInterrupted: " + Thread.currentThread().isInterrupted());
                        i++;
                    }
                    System.out.println(Thread.currentThread().getName() + "结束，" + i);
                }
            });
        }

        try {
//            Thread.sleep(1000);
//            es.shutdown();
            es.shutdownNow();
            es.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
