package com.myLab.corejava.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者实现类
 */
public class ConcreteSubject implements Subject {
    private List<Observer> observerList = new ArrayList<Observer>();

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers(Message message) {
        for(Observer observer : observerList)
            observer.callBack(message);
    }
}
