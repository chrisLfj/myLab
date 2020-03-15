package com.myLab.corejava.thread;

/**
 * 线程的一种编码变体
 */
public class SimpleThread extends Thread {
    private int countDown = 5;
    private static int threadCount = 0;

    public SimpleThread() {
        //调用父类带name参数的构造函数
        super(Integer.toString(++threadCount));
        //构造函数中调用start方法，然后它会运行run方法的逻辑
        start();
    }
    public String toString() {
        return "#" + getName() + "(" + countDown + ")";
    }

    /**
     * 调用自己的run方法
     */
    public void run(){
        while(true){
            System.out.println(this);
            if(--countDown == 0)
                return;
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 5; i++)
            new SimpleThread();
    }
}
