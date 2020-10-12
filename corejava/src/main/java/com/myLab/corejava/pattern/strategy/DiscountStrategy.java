package com.myLab.corejava.pattern.strategy;

/**
 * 策略模式是一种行为型模式，实际项目开发中，这种模式比较常用，最常见的使用场景是利用它来消除冗长的if-else或者switch分支。
 * 它也可以像模板模式那样，提供框架的扩展点。一组策略一般都是为了完成某件事情的不同的逻辑，比如折扣的计算方式，可能会有多种算法
 * 因此策略模式一般会先抽象出一个策略接口来，多个策略类来实现该接口。
 * 工厂模式解耦对象的创建和使用，观察者模式解耦了观察者和被观察者，策略模式则是解耦了策略的定义、创建、使用
 */
public interface DiscountStrategy {
    double calDiscount(double price);
}
