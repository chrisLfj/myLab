package com.mylab.mybatisplus.quickstart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mylab.mybatisplus.quickstart.entity.User;

/**
 * 这里没有任何的注解，mybatis通过MapperScan的配置来加载这些接口的代理类
 */
public interface UserMapper extends BaseMapper<User> {
}
