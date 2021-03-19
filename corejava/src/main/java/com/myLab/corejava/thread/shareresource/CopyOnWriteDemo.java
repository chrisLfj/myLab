package com.myLab.corejava.thread.shareresource;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CopyOnWriteArrayList用法demo，这是一个线程安全的数组类，工作原理是：
 * 1.这个类会持有一个数组对象Object[] array,数据最终会存在这个数组中
 * 2.调用add(E e)方法时，使用自旋锁加锁，获得锁的线程，会先copy一份array，注意新copy出来的数组长度比原array长度多一个
 * Object[] newElements = Array.copyOf(elements, len+1);
 * 3.将新增的元素e放在newElements的最后
 * 4.将array=newElements，指向新的数组，这样array的内容就编程了最新的了
 * 5.释放自旋锁
 *
 */
public class CopyOnWriteDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<String> atomicList = new CopyOnWriteArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 10; i++) {
            executorService.execute(()->{
                for (int j = 0; j < 5; j++) {
                    atomicList.add(Thread.currentThread().getName() + "-" + j);
                    atomicInteger.incrementAndGet();
                }
            });
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("size:" + atomicList.size());
        System.out.println(atomicInteger.get());
    }
}
