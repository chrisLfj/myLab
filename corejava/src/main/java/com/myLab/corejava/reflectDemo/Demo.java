package com.myLab.corejava.reflectDemo;

public class Demo implements Service {
    private String key;//定义私有变量
    public static String code;//定义静态变量

    public Demo() {
        this.key = "Hello World!";
    }

    public Demo(String key) {
        this.key = key;
    }

    @Override
    public void process(int max) {
        System.out.println(max);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        Demo.code = code;
    }

    public static void main(String[] args) {
        for (String a : args) {
            System.out.println(a);
        }
        System.out.println();
    }
}
