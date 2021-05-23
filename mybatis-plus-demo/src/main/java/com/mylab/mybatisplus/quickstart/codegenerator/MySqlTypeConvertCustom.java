package com.mylab.mybatisplus.quickstart.codegenerator;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

public class MySqlTypeConvertCustom extends MySqlTypeConvert implements ITypeConvert {
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        //mybatis plus默认的类型转换类将tinyint(1)类型转换成了Boolean类型，而我们一般情况下是需要将tinyint(1)转换位byte类型
        //这里修改一下tinyint(1)的转换逻辑
        if (fieldType.toLowerCase().contains("tinyint(1)")) {
            return DbColumnType.BYTE;
        }
        return super.processTypeConvert(globalConfig, fieldType);
    }
}
