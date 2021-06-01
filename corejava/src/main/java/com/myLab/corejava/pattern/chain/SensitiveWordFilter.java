package com.myLab.corejava.pattern.chain;

public interface SensitiveWordFilter {
    //这里为了方便示例代码的书写使用了String作为入参，在实际开发中这个入参可以是一个请求的封装对象，当然也可以根据需要传入多个对象
    boolean doFilter(String string);
}
