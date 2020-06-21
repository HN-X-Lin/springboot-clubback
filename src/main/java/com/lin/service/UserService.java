package com.lin.service;

import com.lin.pojo.User;

/**
 * @ClassName UserService
 * @Description
 * @Author xiaolin
 * @Date 2020/6/21 12:30
 * @Version V1.0
 */
public interface UserService {

    public User checkUser(String username, String password);

}
