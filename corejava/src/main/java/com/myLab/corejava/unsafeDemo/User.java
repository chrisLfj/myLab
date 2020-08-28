package com.myLab.corejava.unsafeDemo;

public class User {
    private Integer id;
    private String name;
    public Integer age;

    public String toString(){
        return String.format("id:%s, name:%s, age:%s", id, name, age);
    }
}
