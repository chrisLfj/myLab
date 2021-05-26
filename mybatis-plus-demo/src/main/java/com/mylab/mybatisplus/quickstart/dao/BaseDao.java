package com.mylab.mybatisplus.quickstart.dao;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * mybatis plus的IService接口的自定义扩展接口类，如果IService中提供的接口不满足日常开发需要，可以在这里扩展
 * @param <T>
 */
public interface BaseDao<T> extends IService<T> {
}
