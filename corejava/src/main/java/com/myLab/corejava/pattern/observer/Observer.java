package com.myLab.corejava.pattern.observer;

/**
 * 定义观察者接口，包含回调方法
 */
public interface Observer {
    void callBack(Message message);
}
