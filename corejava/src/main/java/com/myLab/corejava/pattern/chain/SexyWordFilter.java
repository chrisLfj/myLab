package com.myLab.corejava.pattern.chain;

public class SexyWordFilter implements SensitiveWordFilter {
    @Override
    public boolean doFilter(String string) {
        //这里的布尔型legal作为处理之后的结果返回，可以用于是否跳出职责处理链条的判定标准
        boolean legal = true;
        //todo，这里可以写对应的sexyword的过滤逻辑，然后根据情况来改变legal的值
        return legal;
    }
}
