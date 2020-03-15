package com.myLab.corejava.thread.shareresource;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncEventGenerator extends IntGenerator {
    private int currentEventValue = 0;
    private Lock lock = new ReentrantLock();

    /**
     * 在访问临界共享资源的方法上加互斥锁，可以防止该方法被多个任务同时访问
     * 当一个任务访问该方法时，整个SyncEventGenerator对象会被加锁，它可以做到针对某个任务加锁，
     * 其它任务无法访问该对象的任何synchronized方法，只有前一任务从该方法中退出后，其它synchronized方法才可以被访问
     * @return
     */
    @Override
    public synchronized int next() {
        ++currentEventValue;//危险代码，引起多线程访问共享资源
        Thread.yield();
        ++currentEventValue;
        return currentEventValue;
    }

    /**
     * 另外一种方式加锁，即显式的创建Lock对象，在何时的地方锁定，最后必须要释放。
     * 感觉并不如使用对象内置锁性能高，但是这种用法会让代码更灵活。
     * 比如，一个任务在获得对象锁之后又要主动释放锁
     */
//    public int next() {
//        lock.lock();
//        try {
//            ++currentEventValue;//危险代码，引起多线程访问共享资源
//            Thread.yield();
//            ++currentEventValue;
//            return currentEventValue;
//        } finally {
//            lock.unlock();
//        }
//    }


    public static void main(String[] args) {
        EventChecker.test(new SyncEventGenerator(), 10);
    }
}
