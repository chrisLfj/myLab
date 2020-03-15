package com.myLab.corejava.thread.shareresource.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个例子很好的展示了volatile并不能解决访问共享资源冲突的问题
 * Java中如果要修改一个int类型的值，step1:先从主存读到cpu缓存中，step2:在缓存中对其进行操作，step3:写回到主存中，这三个步骤并不具备原子性
 * volatile意味着共享资源只要是被修改了，即使是在step2中缓存中被修改的，它都会刷新主存里的值，保证还未读取共享资源的线程读到的都是最新的值
 * 如果在stpe1完成但step2还未开始这个时刻，另外一个线程访问这个共享资源的话，这时就会出现冲突了，就会得到本例中数组里出现重复数字的结果了
 * 要解决该问题就要保证对共享资源的操作原子性，对nextSerialNumber加synchronized关键字即可
 */
class SerialNumberGenerator{
    private static volatile int serialNumber = 0;
    public static int nextSerialNumber(){
        return serialNumber++;  //线程不安全
    }
}

/**
 * 创建一个数组用，多个线程分别会向该数组中添加数据
 */
class CircularSet{
    private int[] array;
    private int len;
    private int index = 0;

    public CircularSet(int size) {
        array = new int[size];
        len = size;
        for(int i = 0; i < size; i++){
            array[i] = -1;
        }
    }

    public synchronized void add(int i){
        //该方法线程安全，向array数组中添加数字，数组下标等于长度时，下标清零
        array[index] = i;
        index = ++index%len;
    }
    public synchronized boolean contain(int val){
        for(int i = 0; i < len; i++){
            if(array[i] == val)
                return true;
        }
        return false;
    }
}
public class SerialNumberChecker {
    private static final int SIZE = 10;
    private static CircularSet serials = new CircularSet(1000);
    private static ExecutorService exec = Executors.newCachedThreadPool();

    static class SerialChecker implements Runnable{
        @Override
        public void run() {
            while(true){
                int serial = SerialNumberGenerator.nextSerialNumber();
                if(serials.contain(serial)){
                    //如果走到这里，说明多个线程读取了相同的数字
                    System.out.println("Duplicate: " + serial);
                    System.exit(0);
                }
                serials.add(serial);
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < SIZE; i++){
            exec.execute(new SerialChecker());
        }
    }
}
