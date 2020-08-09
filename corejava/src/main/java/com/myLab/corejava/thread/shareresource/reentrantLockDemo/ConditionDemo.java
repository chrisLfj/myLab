package com.myLab.corejava.thread.shareresource.reentrantLockDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition的await方法相比较Object的wait方法更加的灵活，更方便我们根据不同的逻辑来指定线程间通信的策略
 */
public class ConditionDemo implements Runnable {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition1 = lock.newCondition();
    private static final Condition condition2 = lock.newCondition();
    private boolean flag;

    public ConditionDemo(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            //同样的一把锁，却可以根据不同的逻辑分别制定对应的condition条件变量，然后可以让线程阻塞在对应的condition下面
            if(flag){
                System.out.println("Before condition1 await");
                condition1.await();//线程进入WAITING状态
                System.out.println("After condition1 await");
            }else{
                System.out.println("Before condition2 await");
                condition2.await();
                System.out.println("After condition2 await");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ConditionDemo(true));
        executorService.execute(new ConditionDemo(false));
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                boolean b = false;
                lock.lock();
                try {
                    Thread.sleep(1000L);
                    System.out.println("condition1 notifyAll");
                    //同样，在获取了同一把锁之后，可以根据不同的逻辑来决定将哪一个condition下面的线程“唤醒”
                    condition1.signalAll();
                    if(b){
                        Thread.sleep(1000L);
                        System.out.println("condition2 notifyAll");
                        condition2.signalAll();
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println("unlock");
                }
            }
        });
        executorService.shutdown();
    }
}
