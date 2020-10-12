package com.myLab.corejava.pattern.observer;

/**
 * 观察者模式也被称为发布订阅模式，在对象之间定义一对多的依赖，当一个对象状态改变的时候，所有依赖的对象都会收到通知
 * 以下代码是一个经典的观察者模式实现示例，观察者模式实际使用时有很多灵活的用法，从经典示例来看，需要定义很多的类
 * 有时类太多了也是一种“反模式”，因此有时可以将一些类似用户ID等标识放到list中，然后通知时按用户ID来通知，这样可以省去很多的类和实例
 * 设计模式要干的事情就是解耦，创建型模式是将创建和使用解耦，结构型模式是将不同功能代码解耦，行为型模式则是将不同的行为解耦
 * 具体到观察者模式，它将观察者和被观察者的不同行为代码解耦
 */

public class ClassicObserverDemo {
    public static void main(String[] args) {
        ConcreteSubject concreteSubject = new ConcreteSubject();
        concreteSubject.registerObserver(new Observer1());
        concreteSubject.registerObserver(new Observer2());
        /*
        当发生某些变化时，可通知所有的观察者
         */
        Message message = new Message();
        message.setStr("somethins happenned!");
        concreteSubject.notifyObservers(message);
    }
}