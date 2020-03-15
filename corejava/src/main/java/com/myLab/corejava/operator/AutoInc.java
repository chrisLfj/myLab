package com.myLab.corejava.operator;

public class AutoInc {

    public static void main(String[] args) {
        int i = 1;
        System.out.println("i:" + i);
        System.out.println("i:" + ++i);//前缀++，会先执行运算，再生成值，因此直接得到的是运算后的结果
        System.out.println("i:" + i++);//后缀++，会先生成值，再执行运算，因此这里得到的是运算前的状态
        System.out.println("i:" + i);//当再次访问i的时候，得到的是运算后的结果
        System.out.println("i:" + --i);
        System.out.println("i:" + i--);
        System.out.println("i:" + i);
    }
}
