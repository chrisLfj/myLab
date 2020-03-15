package com.myLab.corejava.thread;

/**
 * 线程编码的变体
 * 经常会使用内部类方式来写线程代码
 */
public class InnerThread {
    private int countDown = 5;
    private Thread t;
    public InnerThread(String name) {
        t = new Thread(name){
            public void run(){
                try {
                    while(true){
                        System.out.println(this);
                        if(--countDown==0)
                            return;
                        sleep(10);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            public String toString(){
                return getName() + ": " + countDown;
            }
        };
        t.start();
    }

    public static void main(String[] args) {
        InnerThread thread = new InnerThread("内部类线程");
    }
}
