package com.myLab.corejava.thread.sleepandjoin;

public class YieldDemo implements Runnable{
    @Override
    public synchronized void run() {
        while(true){
            System.out.println(Thread.currentThread().getName() + "进来啦");
            Thread.yield();//线程会让出cpu，进入runnable状态，重新等待分片CPU时间片，但是线程事先拿到了锁，调用yield方法不会释放锁
            System.out.println(Thread.currentThread().getName() + "yield");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        YieldDemo yd = new YieldDemo();
        new Thread(yd, "thread1").start();
        new Thread(yd, "thread2").start();
        Thread.sleep(10000);

        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }
}

/*
做了两个实验：
1.run方法没有加sychronized锁，这时运行结果如下
thread1yield
thread1进来啦
thread2yield
thread2进来啦
thread1yield
thread1进来啦
thread2yield
thread2进来啦
thread1yield
thread1进来啦
thread2yield
thread2进来啦
thread1yield
thread1进来啦
thread2yield
thread2进来啦
从这个结果来看，thred1调用了yield方法之后，thread2可以抢到cpu来执行代码。
2.run方法加上synchronized方法之后，运行结果如下
thread1进来啦
thread1yield
thread1进来啦
thread1yield
thread1进来啦
thread1yield

可以看到，thread1拿到锁之后，调用yield方法，虽然会让出cpu但是它不会释放锁。
thread2即使获得了cpu，由于它无法获得锁，所以无法运行代码。
 */
