package com.myLab.corejava.thread.shareresource;

/**
 * 数字生成器抽象类，通过next方法返回数字，并且通过canceled可以设置停止状态
 */
public abstract class IntGenerator {
    private volatile boolean canceled = false; //volatile确保可视性
    public abstract int next();
    public void cancel(){canceled = true;}
    public boolean isCanceled(){return canceled;}
}
