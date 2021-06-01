package com.myLab.corejava.pattern.chain;

import java.util.ArrayList;
import java.util.List;

/*
职责链模式的结构类，负责将职责链中的各个处理点串联起来，一般有两种实现方式
一种是使用数组结构来存储处理点，并且依次执行
一种是使用链表结构来存储处理点，并且依次执行
 */
public class SensitiveWordFilterChain {
    List<SensitiveWordFilter> filters = new ArrayList<>();

    public void addFilter(SensitiveWordFilter filter) {
        filters.add(filter);
    }

    public boolean filter(String word) {
        for (SensitiveWordFilter filter : filters) {
            if (!filter.doFilter(word)) {
                return false;
            }
        }
        return true;
    }
}
