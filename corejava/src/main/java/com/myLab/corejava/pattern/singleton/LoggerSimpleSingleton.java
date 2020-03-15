package com.myLab.corejava.pattern.singleton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 简单单例模式
 */
public class LoggerSimpleSingleton {
    private FileWriter writer;
    //在类加载时已经实例化好对象了，但这里有个问题，就是在实例化的时候如果想带参数的话就比较麻烦
    private static final LoggerSimpleSingleton instance = new LoggerSimpleSingleton();
    //私有的构造函数可以确保无法在外部实例化该类的对象
    private LoggerSimpleSingleton(){
        File file = new File("xxx.txt");
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //对外提供一个静态方法返回单一实例
    public static LoggerSimpleSingleton getInstance(){
        return instance;
    }

    public void log(String message){
        try {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/**
 * LoggerWithoutPattern类应用示例
 */
class UserController1{
    private LoggerSimpleSingleton logger = LoggerSimpleSingleton.getInstance();
    public void login(String userName, String password){
        //...省略业务逻辑代码...
        logger.log(userName + " login successful!");
    }
}

class OrderController1{
    private LoggerSimpleSingleton logger = LoggerSimpleSingleton.getInstance();
    public void login(String order){
        //...省略业务逻辑代码...
        logger.log("Create an order: " + order);
    }
}
/*
哪些情况下可以考虑使用单例模式？
场景一：处理资源共享的场景，比如以上代码中的日志打印类就是典型的高频使用且存在资源共享的场景
场景二：全局唯一的场景，如果有些数据在系统中只应该保存一份，也比较适合设计成单例类。
       比如配置信息类，在系统中只需要一个配置文件
       再比如唯一递增ID号码生成器，如果系统中存在两个生成器对象，那就会存在生成重复ID的情况

以上是一个简单单例模式(也可以叫饿汉式单例)的实现方式，在类加载的时候就已经实例化出一个对象，通过私有化的构造函数
来防止外部new对象，这样可以确保所有线程都只共享一个对象，这样只需要加对象锁就可以达到线程安全的目的
但简单单例模式也有些问题，比如，无法实现延迟加载，并且如果想带参数实例化，也不太方便
 */
