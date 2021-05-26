package com.mylab.mybatisplus.quickstart.dao.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mylab.mybatisplus.quickstart.dao.BaseDao;

/**
 * 抽象Dao层实现类，该类继承了mybatis plus框架中原生的ServiceImpl类的所有行为，同时又实现自定义扩展接口类，对扩展接口进行实现
 * @param <M>
 * @param <T>
 */
public class AbstractDaoImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements BaseDao<T> {
}
