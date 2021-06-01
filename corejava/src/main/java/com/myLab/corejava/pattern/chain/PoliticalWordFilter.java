package com.myLab.corejava.pattern.chain;

public class PoliticalWordFilter implements SensitiveWordFilter {
    @Override
    public boolean doFilter(String string) {
        boolean legal = true;
        //todo，这里可以写对应的political word的过滤逻辑，然后根据情况来改变legal的值
        return legal;
    }
}
