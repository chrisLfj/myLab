package com.myLab.corejava.thread.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++){
            Future<String> future = exec.submit(new TaskWithResult(i));
//            future.isDone();//不会阻塞main线程
//            System.out.println("continue");
            try {
                //调用get()方法，该线程将阻塞，直至结果准备就绪，这时线程调度器可以切换到其他线程。
                //阻塞线程不是一个好的方式，因此还提供了具有超时的get()或者调用isDone()来查看任务是否完成
                //还可以利用future的cancel(boolean mayInterruptIfRunning)来停止/取消任务，如果任务已经完成，已经停止或者出于一些原因无法停止，
                //则停止失败，该方法返回false；如果任务还未运行则取消任务永远不执行；如果任务已经开始运行，通过方法的布尔类型参数来决定是否Interrupt它还是让它运行完
                System.out.println(future.get());//get方法会阻塞main线程，它是个同步方法

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
}
