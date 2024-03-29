package com.myLab.corejava.thread.shareresource.synchronizedDemo;

/**
 * 演示synchronized关键字的锁范围，
 */
public class SafeShareUseSyncDemo5 implements Runnable{
    private Account accountA;
    private Account accountB;

    public SafeShareUseSyncDemo5(Account accountA, Account accountB) {
        this.accountA = accountA;
        this.accountB = accountB;
    }

    public void run() {
        if(Thread.currentThread().getName().contains("1")){
            synchronized (accountA){
                System.out.println(Thread.currentThread().getName() + "进入同步代码块，对accountA进行操作");
                accountA.setPrice(accountA.getPrice() + 500);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                accountA.setPrice(accountA.getPrice() - 500);
                System.out.println(Thread.currentThread().getName() + "操作结束");
            }
        } else if (Thread.currentThread().getName().contains("3")) {
            synchronized(this){
                System.out.println(Thread.currentThread().getName() + "进入同步方法，开始休眠");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "同步方法执行结束");
            }
        } else if (Thread.currentThread().getName().contains("4")) {
            synch();
        } else {
            synchronized (accountB) {
                System.out.println(Thread.currentThread().getName() + "进入同步代码块，对accountB进行操作");
                accountB.setPrice(accountB.getPrice() + 500);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                accountB.setPrice(accountB.getPrice() - 500);
                System.out.println(Thread.currentThread().getName() + "操作结束");
            }
        }

    }

    public synchronized void synch(){
        System.out.println(Thread.currentThread().getName() + "进入同步方法，开始休眠");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "同步方法执行结束");
    }

    public void unSynch(){
        System.out.println(Thread.currentThread().getName() + "进入非同步方法，开始休眠");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "非同步方法执行结束");
    }

    public Account getAccountA() {
        return accountA;
    }

    public void setAccountA(Account accountA) {
        this.accountA = accountA;
    }

    public Account getAccountB() {
        return accountB;
    }

    public void setAccountB(Account accountB) {
        this.accountB = accountB;
    }

    public static void main(String[] args) {
        SafeShareUseSyncDemo5 us1 = new SafeShareUseSyncDemo5(new Account(1000), new Account(1500));
//        SafeShareUseSyncDemo5 us2 = new SafeShareUseSyncDemo5(new Account(1000), new Account(1500));

        new Thread(us1, "Thread" + 1).start();
        new Thread(us1, "Thread" + 11).start();
        new Thread(us1, "Thread" + 2).start();
        new Thread(us1, "Thread" + 3).start();
        new Thread(us1, "Thread" + 4).start();

    }

    static class Account {
        private double price;

        public Account(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
/*
synchronized修饰指定对象，锁的粒度是该指定对象，同一时刻只能有一个线程访问该对象，其它试图访问该对象的线程需要等待
从执行结果来看，Thread1先拿到了accountA的锁进行操作，而Thread11也试图拿accountA的锁，它只能等待Thread1线程执行完毕才可以执行。
而Thread2它会去拿accountB的锁，它与Thread1并不冲突，所以Thread2不需要等待，可以马上执行。
同理Thread3会试图去拿this，即当前实例的锁，与accountA和accountB都不冲突，因此它也无需等待，可以马上执行。
但是Thread4访问的是一个同步方法，同步方法也是需要拿到当前实例的锁，因此它会被Thread3阻塞住，直到Thread3释放了当前实例的锁，Thread4才能继续执行
Thread1进入同步代码块，对accountA进行操作
Thread2进入同步代码块，对accountB进行操作
Thread3进入同步方法，开始休眠
Thread1操作结束
Thread2操作结束
Thread11进入同步代码块，对accountA进行操作
Thread11操作结束
Thread3同步方法执行结束
Thread4进入同步方法，开始休眠
Thread4同步方法执行结束
 */
