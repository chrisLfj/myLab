package com.myLab.corejava.thread.shareresource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * EventChecker任务依赖intGenerator得到整数
 */
public class EventChecker implements Runnable {
    private IntGenerator generator;
    private final int id;

    public EventChecker(IntGenerator generator, int id) {
        this.generator = generator;
        this.id = id;
    }

    @Override
    public void run() {
        //如果generator的iscanceled方法一直返回false，那么就会永远循环下去
        while(!generator.isCanceled()){
            int val = generator.next();
            System.out.println(val);
            //只要发现generator返回值不是偶数，就会设置cancel，然后任务结束
            if(val % 2 != 0){
                System.out.println(val);
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator g, int count){
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < count; i++){

            exec.execute(new EventChecker(g, i));
        }
        exec.shutdown();
    }

}
