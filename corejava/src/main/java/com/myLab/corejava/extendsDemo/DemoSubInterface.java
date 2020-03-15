package com.myLab.corejava.extendsDemo;

import java.util.List;

/**
 * 接口可以继承多个接口，并且可以不实现父接口方法
 */
public interface DemoSubInterface extends DemoInterface {
    void shutdown();

    List<Runnable> shutdownNow();
}
