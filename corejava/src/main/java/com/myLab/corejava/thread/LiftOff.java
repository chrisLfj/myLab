package com.myLab.corejava.thread;

public class LiftOff implements Runnable {
    protected int countDown = 10;
    private static int taskCount = 0;
    //第一次new LiftOff实例时，id = 0，tasCount加一运算后变为1
    //第二次new LiftOff实例时，id = 1, 因为taskCount为静态变量，实例共享
    //第三次new LiftOff实例时，id = 2
    //......
    private final int id = taskCount++;
    public LiftOff() {

    }
    public LiftOff(int countDown) {
        this.countDown = countDown;
    }
    public String status(){
        return "#" + id + "(" + (countDown>0?countDown:"Liftoff!") + ").";
    }
    @Override
    public void run() {
        while (countDown-- > 0){
            System.out.println(status());
            //通知线程调度器，可以切换给其他线程执行，这是选择性的是一种暗示，并不代表它会立即切换
            //使用了yield方法之后，会有一个分布良好的处理机制，即一个线程运行过程中，另外的线程也有机会获得运行
            //如果去掉yield方法，可以运行看结果如何，基本上是一个线程运行完，其它线程才会开始运行。
            Thread.yield();
        }
    }
}
