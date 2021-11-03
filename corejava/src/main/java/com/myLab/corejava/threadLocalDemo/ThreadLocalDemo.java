package com.myLab.corejava.threadLocalDemo;

/**
 * Threadlocal能实现变量的线程隔离么？答案是不一定
 * 这个要根据ThreadLocal中存放的内容来判断，
 * 如果存放的是基础类型或者其对应的封装类型是可以实现隔离的，比如下面实例代码，如果shareNum类型是Integer的话，那每个ThreadLocal对象中会持有其副本，可以实现隔离
 * 如果存放的是对象则无法实现隔离，因为threadlocal存的是引用类型，对象的值如果在外部被改变了，那通过threadlocal来获取该对象时，会发现它也随之改变了
 * 因此对于threadlocal的使用需要注意，如果要存放对象的话，那是不是应该先将其深度clone，然后将clone的对象存入threadlocal中
 * 所以threadlocal是无法实现共享变量的线程安全的，它真正的目的是将变量的范围控制在线程内部，然后别的线程访问不到这个变量，
 * 这就要求该变量需要在线程内部创建出来，然后放在threadlocal中实现该线程中的程序对此变量共享，然后随着线程的消亡而回收
 * 例如spring jdbc connection放到threadlocal，它就是在线程内部创建连接，保证事务中获取的是同一个连接
 * Threadlocal是如何实现线程变量的本地隔离？ThreadLocalMap是关键
 * 首先我们实例化一个ThreadLocal对象，调用对象的set方法，将某个变量设置为线程变量，
 * set方法内部会首先尝试获取当前线程的ThreadLocalMap变量(每个Thread对象都会拥有一个ThreadLocalMap变量)，如果ThreadLocalMap变量为null，则会创建一个ThreadLocalMap对象
 * ThreadLocalMap变量本质上是一个map结构，它的key就是ThreadLocal对象的引用，value则保存的是实际的变量值。
 * 由此可见，通过ThreadLocalMap作为Thread的实例变量，每个Thread实例都有自己的ThreadLocalMap变量，而它内部存放着ThreadLocal对象引用和实际变量值这个key-value键值对
 * 也就是说，同一个ThreadLocal对象引用可以分别作为key存放在不同的Thread对象的ThreadLocalMap中，而这里的关键是ThreadLocalMap是线程隔离的
 * get方法的逻辑是，首先获取当前线程的ThreadLocalMap变量，然后以ThreadLocal对象引用为key，查找到对应的value。
 */
public class ThreadLocalDemo {
//    private static Integer shareNum = 0;
    private static ShareNum shareNumObj = new ShareNum();
//    public static ThreadLocal tlShareNum = new ThreadLocal();
    public static ThreadLocal tlShareNumObj = new ThreadLocal();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
//            ThreadLocalDemo.tlShareNum.set(ThreadLocalDemo.shareNum);
            ThreadLocalDemo.tlShareNumObj.set(ThreadLocalDemo.shareNumObj);
            for (int i = 0; i < 50; i++) {
//                System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalDemo.tlShareNum.get());
                System.out.println(Thread.currentThread().getName() + ":" + ((ShareNum)ThreadLocalDemo.tlShareNumObj.get()).getSharNum());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"thread1");
        Thread thread2 = new Thread(() -> {
//            ThreadLocalDemo.tlShareNum.set(ThreadLocalDemo.shareNum);
            ThreadLocalDemo.tlShareNumObj.set(ThreadLocalDemo.shareNumObj);
            for (int i = 0; i < 50; i++) {
//                System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalDemo.tlShareNum.get());
                System.out.println(Thread.currentThread().getName() + ":" + ((ShareNum)ThreadLocalDemo.tlShareNumObj.get()).getSharNum());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread2");
        thread1.start();
        thread2.start();
        for (int i = 0; i < 10; i++) {
//            ThreadLocalDemo.shareNum++;
            ThreadLocalDemo.shareNumObj.setSharNum(i);
            Thread.sleep(100);
//            System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalDemo.shareNum);
            System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalDemo.shareNumObj.getSharNum());
        }
    }
}
