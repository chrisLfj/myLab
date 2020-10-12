package com.myLab.corejava.pattern.observer;

/**
 * 定义被观察者接口，包含注册观察者，删除观察者，通知观察者
 */
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Message message);
}
