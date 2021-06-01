package com.myLab.corejava.pattern.chain;

import com.fasterxml.jackson.core.filter.FilteringGeneratorDelegate;

/**
 * 我们模拟一个框架的filter实现，框架中实现职责链，在进入业务处理之前对输入进行一些列的检查和过滤
 */
public class ApplicationDemo {
    public static void main(String[] args) {
        SensitiveWordFilterChain filterChain = new SensitiveWordFilterChain();
        filterChain.addFilter(new SexyWordFilter());
        filterChain.addFilter(new PoliticalWordFilter());
        boolean legal = filterChain.filter("some words");
        if (!legal) {
            //todo, 过滤结果失败，拒绝请求
        } else {
            //todo，过滤结果成功，则进入业务代码中
        }
    }
}
