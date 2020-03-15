package com.myLab.corejava.extendsDemo;

import java.util.List;

/**
 * 一个普通的类继承一个抽象类，它必须实现抽象类的抽象方法以及所有interface中没有被实现的接口
 */
public class SubClass extends AbstractClass{
    @Override
    void abstractMethod() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public void execute(Runnable runnable) {

    }
}
