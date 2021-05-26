package com.mylab.mybatisplus.quickstart.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum GenderEnum {
    MALE(1, "男"),
    FEMALE(2,"女");

    @EnumValue
    private final int code;
    private final String desp;

    GenderEnum(int code, String desp) {
        this.code = code;
        this.desp = desp;
    }
}
