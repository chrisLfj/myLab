package com.myLab.corejava.lab;

import java.io.IOException;
import java.nio.ByteBuffer;

public class DirectMemoryTest {
    public void genSomeDirectMemory(){
        try {
            Thread.sleep(20000);
            ByteBuffer.allocateDirect(500*1024*1024);//申请分配500M直接内存
            System.out.println("sleep 10s");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 每次调用该方法时，都会占用300M的对内存，如果新生代<300M，新生代不足，那么在方法执行过程中就会触发minorGC，
     * 此时由于方法还没有执行完，这些对象不会被回收，而是会移到老年代中，因此，每调用一次该方法都会向老年代中移动一些对象，
     * 直到触发老年代oldGC。
     */
    public void catchSomeObjToOld(){
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        for(int i = 0; i < 300; i++){
            cachedGarbage[i] = new byte[1024*1024];
        }
    }
    public static void main(String[] args) {
        try {


//            // 当前毫秒时间戳
//            long startMillis = System.currentTimeMillis();
//            // 持续运行毫秒数; 可根据需要进行修改
//            long timeoutMillis = TimeUnit.SECONDS.toMillis(10);
//            // 结束时间戳
//            long endMillis = startMillis + timeoutMillis;
//            int cacheSize = 2000;
//            Object[] cachedGarbage = new Object[cacheSize];
//            while (System.currentTimeMillis() < endMillis) {
//                // 生成垃圾对象
//                Object garbage = generateGarbage(100*1024);
//                counter.increment();
//                int randomIndex = random.nextInt(2 * cacheSize);
//                if (randomIndex < cacheSize) {
//                    cachedGarbage[randomIndex] = garbage;
//                }
//            }
            DirectMemoryTest directMemoryTest = new DirectMemoryTest();
            directMemoryTest.genSomeDirectMemory();
            for(int i = 0; i < 5; i++){
                System.out.println("catch some object to old");
                directMemoryTest.catchSomeObjToOld();
                Thread.sleep(2000);
            }

//            System.gc();
//            Thread.sleep(20000);
            System.out.println("over");
            System.in.read();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
