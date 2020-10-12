package com.myLab.corejava.pattern.observer;

public class Message {
    private String Str;

    public void setStr(String str) {
        Str = str;
    }

    @Override
    public String toString() {
        return "Message{" +
                "Str='" + Str + '\'' +
                '}';
    }
}
