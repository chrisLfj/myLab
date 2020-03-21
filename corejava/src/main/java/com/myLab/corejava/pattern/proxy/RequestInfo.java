package com.myLab.corejava.pattern.proxy;

public class RequestInfo {
    private String requestType;
    private long startTime;
    private long endTime;

    public RequestInfo(String requestType, long startTime, long endTime) {
        this.requestType = requestType;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
