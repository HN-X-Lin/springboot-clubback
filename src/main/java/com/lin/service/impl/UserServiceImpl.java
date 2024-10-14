package com.lin.service.impl;

import com.lin.dao.UserDao;
import com.lin.pojo.User;
import com.lin.service.UserService;
import com.lin.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author xiaolin
 * @Date 2020/6/21 12:30
 * @Version V1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User queryUser(String username) {
        User user = userDao.queryUser(username);
        return user;
    }

}
