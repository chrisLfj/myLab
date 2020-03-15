package com.myLab.corejava.extendsDemo;

public class Cleanser {
    private String s = "Cleanser";
    protected String ps = "Cleanser protected String";
    public Cleanser(){
        System.out.println("实例化父类Cleanser");
    }
    public void append(String a){
        s+=a;
    }
    public void dilute(){
        append(" dilute()");
    }
    public void apply(){
        append(" apply()");
    }
    public String toString(){
        return s;
    }
}
