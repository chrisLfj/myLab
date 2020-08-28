package com.myLab.corejava.unsafeDemo;

import sun.misc.Unsafe;
import sun.reflect.Reflection;

import java.lang.reflect.Field;

/**
 * Unsafe类使java拥有了像C语言指针一样操作内存空间的能力，同时也带来指针的问题
 * 但是不可否认的一点是Unsafe确实能实现一些不那么常见的功能
 * 很多框架像Netty，包括JAVA本身的一些类中都较多的使用了Unsafe类的能力
 * 概括起来Unsafe主要有以下几个方面的功能：
 * 操作对象属性
 * 操作数组元素
 * 线程挂起和恢复、CAS
 * 本类中就会集中演示一些Unsafe类的常用方法
 */
public class UnsafeDemo {
    private static Unsafe unsafe = null;
    static {
        //unsafe使用了懒汉式的单例模式,但是在这里如果直接用Unsafe.getUnsafe()方法获取unsafe实例的时候会抛出SecurityException异常
        //原因是Unsafe类的getUnsafe()方法中会要求当前类加载器为SystemDomainLoader，如果不是这个加载器的话就会抛出SecurityException异常
        //而我们一般的程序java类，类加载器基本都是AppClassLoader，扩展包的类加载器一般是ExtClassLoader，所以这里直接想要得到unsafe实例的话就会抛出SecurityException
        //如何判断类的加载器是哪个呢，比如我们随便new一个Object实例，然后通过调用该object的getClass().getClassLoader()方法返回的就是该类的加载器，如果返回的是null，就表明该类的加载器是SystemDomainLoader
//        unsafe = sun.misc.Unsafe.getUnsafe();

        //所以这里可以通过反射方式来获取Unsafe实例
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);//theUnsafe是private的，要访问它需要将accessible设置成true
            unsafe = (Unsafe)field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * unsafe操控对象属性
     * @throws NoSuchFieldException
     */
    public void handleObjectField() throws NoSuchFieldException {
        User user = new User();
        //反射获取User的属性
        Field id = user.getClass().getDeclaredField("id");
        Field age = user.getClass().getDeclaredField("age");
        //通过unsafe类能力获得user这个实例中对应属性的内存偏移地址
        long idOffset = unsafe.objectFieldOffset(id);
        long ageOffset = unsafe.objectFieldOffset(age);
        //属性是否可读不管是public还是private都可以修改
        unsafe.putObject(user, idOffset, 1);
        unsafe.putObject(user, ageOffset, 20);
        System.out.println(user);
    }

    public static void main(String[] args) throws NoSuchFieldException {
        UnsafeDemo unsafeDemo = new UnsafeDemo();
        unsafeDemo.handleObjectField();
    }
}
