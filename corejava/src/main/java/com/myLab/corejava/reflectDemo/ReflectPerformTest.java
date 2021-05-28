package com.myLab.corejava.reflectDemo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射的代价：
 * 1.获取字段，属性，方法信息
 * 2.装箱，拆箱，反射调用方法传入的是Object[]，返回的也是Object类型，需要检查参数数量、类型、需要装箱
 * 3.安全性检查 某些方法调用需要安全访问权限，而且还需要做方法调用权限的检查，例如public，private等权限检查
 * 以上这些代价，在不适用反射时是不存在的，因为java类文件编译成字节码文件之后，很多信息是明确的，并且也会经过加载，校验，准备，解析，初始化这些阶段
 * 很多工作在这些阶段中已经完成了。
 * 优化方法：
 * 1、运行时缓存类型信息，其实class类一经加载就会存在于堆中
 * 2、创建委托代理、表达式树、本质上都是动态生成了强类型的中间代码
 */
public class ReflectPerformTest {
    public int operate(int input) {
        return ++input;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ReflectPerformTest r = new ReflectPerformTest();

        Class rClass = ReflectPerformTest.class;
        Object obj = rClass.newInstance();
        Method operate = rClass.getMethod("operate",int.class);
        int result = 0;
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
//            result = r.operate(result);
            result = (int)operate.invoke(obj, result);//反射的入参和返回都是Object，这是为了实现通用性的结果，这就涉及到类型的转换，拆箱装箱的损耗，这里如果去掉(int)的转换，效率会提升不小
        }
        System.out.println(result);
        Long endTime = System.currentTimeMillis();
        System.out.println("cost time: " + (endTime - startTime));
    }
}
