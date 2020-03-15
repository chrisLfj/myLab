package com.myLab.corejava.thread.shareresource;

public class EventGenerator extends IntGenerator {
    private int currentEventValue = 0;

    /**
     * 正常情况，调用一次next方法肯定会返回偶数，但是在不加防范的情况下，多线程访问时，情况就不可控了
     * @return
     */
    @Override
    public int next() {
        ++currentEventValue;//危险代码，引起多线程访问共享资源
        Thread.yield();
        ++currentEventValue;
        return currentEventValue;
    }

    public static void main(String[] args) {
        EventChecker.test(new EventGenerator(), 10);
    }
}
