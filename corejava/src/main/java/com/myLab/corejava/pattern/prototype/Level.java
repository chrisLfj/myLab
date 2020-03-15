package com.myLab.corejava.pattern.prototype;

public enum Level {
    DEBUG("DEBUG", 0),
    INFO("INFO", 1),
    WARNING("WARNING", 2),
    ERROR("ERROR", 3);

    private String level;
    private Integer code;

    private Level(String level, Integer code) {
        this.level = level;
        this.code = code;
    }

    public int codeValue(){
        return code;
    }
}
