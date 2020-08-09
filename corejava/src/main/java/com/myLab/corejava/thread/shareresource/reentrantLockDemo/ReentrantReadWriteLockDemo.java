package com.myLab.corejava.thread.shareresource.reentrantLockDemo;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static Deque<Integer> data = new LinkedList<>();
    private static int value;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //每个线程往队列里添加10个数字
                    for(int i = 0; i < 10; i++){
                        lock.writeLock().lock();
//                        lock.readLock().lock();//用读锁也试试，看看结果如何
                        data.addFirst(++value);
                        lock.writeLock().unlock();
//                        lock.readLock().unlock();
                    }
                }
            });
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    //每个线程从队列尾读取10个数字
//                    for(int i = 0; i < 10; i++){
//                        Integer v = null;
//                        do{
//                            lock.readLock().lock();
////                            lock.writeLock().lock();//用writelock试试
//                            v = data.pollLast();
//                            if(v == null)
//                                Thread.yield();
//                            else {
//                                System.out.println("读到数据：" + v);
//                                break;
//                            }
//                            lock.readLock().unlock();
////                            lock.writeLock().unlock();
//                        }while(true);
//                    }
//                }
//            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10L, TimeUnit.SECONDS);
        System.out.println("队列里最终的值：");
        for(Integer i : data)
            System.out.println(i);
    }

}
