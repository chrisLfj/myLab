package com.myLab.corejava.pattern.singleton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 一个简单的日志记录类，没有使用任何模式
 */
public class LoggerWithoutPattern {
    private FileWriter writer;

    public LoggerWithoutPattern(String filePath) {
        File file = new File(filePath);
        try {
            writer = new FileWriter(file, true);//true代表追加写入
        } catch (IOException e) {
            e.printStackTrace();
        }
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
class UserController{
    private LoggerWithoutPattern logger = new LoggerWithoutPattern("xxx.txt");
    public void login(String userName, String password){
        //...省略业务逻辑代码...
        logger.log(userName + " login successful!");
    }
}

class OrderController{
    private LoggerWithoutPattern logger = new LoggerWithoutPattern("xxx.txt");
    public void login(String order){
        //...省略业务逻辑代码...
        logger.log("Create an order: " + order);
    }
}

/*
上面代码会出现什么问题？
问题1：每次使用logger的时候都要new一个对象出来，因为logger在程序中是一个高频使用的功能，机会每个类都需要使用日志功能，因此就会new非常多的logger对象
       这个对系统性能和内存的消耗都是有影响的。
问题2：单线程中运行没有任何问题，但是在多线程环境下就会出现日志文件被其它线程覆盖的情况，因为日志文件在这个场景下是多线程之间的共享资源。
       要解决这个问题，我们常见思路是给log()函数加上互斥锁，如下：
       public void log(String message){
        synchronized(this){   //对象级别锁
            try {
                writer.write(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       }
       但这个解决方式有个问题，就是synchronized(this)是在对象上加锁，是对象级别的锁，也就是说同一个logger对象被多个线程使用时是线程安全的，
       但如果像上面代码那样new了多个logger对象被多个线程使用的话依然无法解决共享的日志文件被覆盖的问题。想进一步解决这个问题，就需要在类上加锁，这样这个类的所有对象就都是线程安全的
       public void log(String message){
        synchronized(Logger.class){   //类级别锁
            try {
                writer.write(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       }
      除了使用类级别锁之外，实际上，解决资源竞争问题的办法还有很多，分布式锁是最常听到的一种解决方案。不过，实现一个安全可靠，无bug，高性能的分布式锁，并不是一件容易的事
      除此之外，并发队列(比如java中的BlockingQueue)也可以解决这个问题：即多个线程往并发队列中写日志，一个单独的线程负责将并发队列中的数据，写入到日志文件中
由以上两个问题可以看出，解决方案都有些复杂，如果使用单例模式的话则会容易的多，对于Logger这种常用对象，将其设计成单例，即程序中只允许创建一个Logger对象，所有线程都共享这
一个Logger对象，共享一个FileWriter对象(FileWriter本身就是对象级别安全的)，也就避免了多线程情况下日志会互相覆盖的问题，而且在实现上也比较简单.
 */

