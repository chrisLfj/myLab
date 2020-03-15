package com.myLab.corejava.pattern.prototype.after;

import com.myLab.corejava.pattern.prototype.Level;

/**
 * 模板模式
 * 利用java抽象类，将重复代码放到抽象类中实现，将变化的代码交给子类实现
 */
public abstract class Logger {
    private String name;
    private boolean enabled;
    private Level minPermittedLevel;

    public Logger(String name, boolean enabled, Level minPermittedLevel) {
        this.name = name;
        this.enabled = enabled;
        this.minPermittedLevel = minPermittedLevel;
    }

    /*log方法封装了公共步骤，比如所有子类都会判断loggable，这种方式就像一个模板一样*/
    public void log(Level level, String message){
        boolean loggable = enabled && minPermittedLevel.codeValue() <= level.codeValue();
        if(!loggable)
            return;
        //doLog方法封装变化，这里会调用子类的doLog方法
        doLog(level, message);
    }
    //doLog方法中封装的是子类的多态实现，所以使用protected修饰，它只能被子类访问，不会开放给其它类访问
    protected abstract void doLog(Level level, String message);
}
