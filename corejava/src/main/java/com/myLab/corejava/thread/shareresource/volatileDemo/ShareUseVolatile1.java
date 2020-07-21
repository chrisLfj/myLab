package com.myLab.corejava.thread.shareresource.volatileDemo;

/**
 * volatile一般的使用场景是信号量更新，怎么理解？
 * 例如，线程A需要依赖一个信号量的更新来进行逻辑，而这个信号量的更新由另外一个线程B负责，为了让信号量的更新第一时间让线程A感知到，
 * 这时就可以考虑使用volatile来修饰这个信号量，让更新马上刷新主存，然后让线程尽快可见。
 */
public class ShareUseVolatile1 implements Runnable{
    private volatile boolean isCancel;

    public ShareUseVolatile1() {
    }

    public void run() {
        Long start = System.currentTimeMillis();
        while(!isCancel){
            System.out.println("信号量还未置为true");
        }
        Long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end-start));
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public static void main(String[] args) {
        ShareUseVolatile1 sv = new ShareUseVolatile1();
        new Thread(sv, "thread1").start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                sv.setCancel(true);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread2").start();
    }
}

