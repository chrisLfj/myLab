package com.mylab.mybatisplus.quickstart.dao.impl;

import com.mylab.mybatisplus.quickstart.entity.User;
import com.mylab.mybatisplus.quickstart.mapper.UserMapper;
import com.mylab.mybatisplus.quickstart.dao.UserDao;
import com.mylab.mybatisplus.quickstart.dao.impl.AbstractDaoImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liufj
 * @since 2021-05-26
 */
@Service
public class UserDaoImpl extends AbstractDaoImpl<UserMapper, User> implements UserDao {

}
