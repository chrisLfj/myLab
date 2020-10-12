package com.myLab.corejava.pattern.observer;

public class Observer2 implements Observer {

    @Override
    public void callBack(Message message) {
        System.out.println(this.getClass().getName() + ":" + message);
    }
}
