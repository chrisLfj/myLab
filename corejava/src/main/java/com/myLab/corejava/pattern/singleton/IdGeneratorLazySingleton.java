package com.myLab.corejava.pattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 使用懒汉单例模式实现一个ID生成器
 */
public class IdGeneratorLazySingleton {
    //AtomicLong可以提供一个线程安全并且满足原子性的Long类型
    private AtomicLong id = new AtomicLong(0);
    private static IdGeneratorLazySingleton instance;
    private IdGeneratorLazySingleton(){};
    //getInstance方法上加锁，由于该方法是静态方法(相当于一把类级别锁)，因此所有线程调用该方法时相当于是串行操作了，并发度很低
    public static synchronized IdGeneratorLazySingleton getInstance(){
        if(instance == null)
            instance = new IdGeneratorLazySingleton();
        return instance;
    }
    public long getId(){
        return id.incrementAndGet();
    }
}
