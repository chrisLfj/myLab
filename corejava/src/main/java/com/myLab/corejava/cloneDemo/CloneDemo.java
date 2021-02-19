package com.myLab.corejava.cloneDemo;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * java的类属性包括基本属性和引用属性，
 * 基本属性是指char，short，int，double，long，boolean等等这些
 * 引用属性则是指对一个对象实例的引用，它指向堆中该实例地址
 * 浅克隆：是指两个对象实例之间复制基本属性和引用属性，这就意味着这两个对象中的引用属性会指向同一个实例地址
 * 深度克隆：则是将引用的对象实例再复制出一份，这样两个对象之中的引用属性就不会指向同一个对象实例了
 */
public class CloneDemo {
    public void testCopyProperties() throws InvocationTargetException, IllegalAccessException {
        Teacher liu = new Teacher("Liu", 30);
        Student alan = new Student("alan", 12, liu);
        Student eva = new Student();
        System.out.println("clone前：" + alan);
        //BeanUtils的copyProperties实现的是浅克隆
        BeanUtils.copyProperties(eva, alan);
        System.out.println("clone后alan：" + alan);
        System.out.println("clone后eva：" + eva);
        System.out.println("是否引用同一个老师？" + (alan.getTeacher() == eva.getTeacher()));
    }

    public void testclone() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Teacher liu = new Teacher("Liu", 30);
        Student alan = new Student("alan", 12, liu);
        //BeanUtils的cloneBean方法也是浅克隆
        Student eva = (Student) BeanUtils.cloneBean(alan);
        System.out.println("clone后alan：" + alan);
        System.out.println("clone后eva：" + eva);
        System.out.println("是否引用同一个老师？" + (alan.getTeacher() == eva.getTeacher()));
    }

    /**
     * 深度clone有两种方式
     * 方式1：每个类需继承Cloneable，重写clone方法，其中super.clone方法意味着浅克隆，它会为当前对象复制一份出来，基本类型的属性互不影响，引用类型的属性则指向同一个引用地址
     * 这个例子中，student中引用了一个teacher类型，如果要实现深度clone，则需要Teacher也实现cloneable接口并重写clone方法
     * 方式2：利用序列化方式来实现深度克隆，但是需要Student类实现Serializable接口
     */
    public void testDeepClone() {
        Teacher liu = new Teacher("Liu", 30);
        Student alan = new Student("alan", 12, liu);
        Student eva = alan.clone();
        System.out.println("clone后alan：" + alan);
        System.out.println("clone后eva：" + eva);
        System.out.println("是否引用同一个老师？" + (alan.getTeacher() == eva.getTeacher()));
    }


    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        CloneDemo cloneDemo = new CloneDemo();
//        cloneDemo.testclone();
        cloneDemo.testDeepClone();
    }
}
