package com.myLab.corejava.extendsDemo;

/**
 * 抽象类只能实现一个接口，但是它却可以不用实现接口方法
 * 抽象类与接口很像，可以声明抽象方法让继承者去实现
 * 但是它总归是一个类，它只能实现一个接口，并且可以有自己的变量和方法，它只可以被其他类extends
 */
public abstract  class AbstractClass implements DemoSubInterface {

    abstract void abstractMethod();

}
