package com.myLab.corejava.thread.callable;

import java.util.concurrent.Callable;

/**
 * 一般在写线程的执行任务时会实现Runnable，但是它的run方法是没有返回值的。如果希望在完成任务的时候能够返回一个值
 * 那么可以实现Callable接口，它具有类型参数的泛型，并且必须使用ExecutorService.submit()方法调用它
 */
public class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        if(id % 2 == 0){
            System.out.println("sleep for 1s");
            Thread.sleep(1000);
        }
        System.out.println(Thread.currentThread().getName() + "is touched, id: " + id);
        return "result of TaskWithResult:" + id;
    }
}
