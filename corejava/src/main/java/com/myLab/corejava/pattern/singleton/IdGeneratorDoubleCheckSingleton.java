package com.myLab.corejava.pattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 使用双重检验单例模式实现一个ID生成器，双重检验模式其实是在懒汉单例模式基础上做了一些提高并发度的处理
 */
public class IdGeneratorDoubleCheckSingleton {
    //AtomicLong可以提供一个线程安全并且满足原子性的Long类型
    private AtomicLong id = new AtomicLong(0);
    private static IdGeneratorDoubleCheckSingleton instance;
    private IdGeneratorDoubleCheckSingleton(){};

    public static IdGeneratorDoubleCheckSingleton getInstance(){
        if(instance == null)
            //将synchronized下沉到if语句中，可以减少锁的范围，从而增加并发度
            synchronized(IdGeneratorDoubleCheckSingleton.class){
                /*
                为什么要双重检测if？试想如果两个线程A和B并发都执行到了第17行，这时只能有一个线程拿到锁继续执行
                假设线程A拿到了锁，继续执行，如果没有if(instance==null)判断，它直接new了一个对象返回并且使用该对象继续做事情。
                这时A释放了锁，线程B拿到了锁，继续执行，如果没有if(instance==null)判断，线程B会再次new一个对象返回并且使用该对象继续做事情。
                注意这时就存在new出了两个对象了，就会出问题，这个ID生成器就会生成重复ID，这就不是单例模式了
                 */
                if(instance == null)
                    instance = new IdGeneratorDoubleCheckSingleton();
            }
        return instance;
    }
    public long getId(){
        return id.incrementAndGet();
    }
}
