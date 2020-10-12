package com.myLab.corejava.pattern.observer;

/**
 * 定义多个观察者类
 */
public class Observer1 implements Observer {

    @Override
    public void callBack(Message message) {
        System.out.println(this.getClass().getName() + ":" + message);
    }
}
