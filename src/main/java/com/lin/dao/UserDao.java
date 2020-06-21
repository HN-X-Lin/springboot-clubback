package com.lin.dao;

import com.lin.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserDao
 * @Description
 * @Author xiaolin
 * @Date 2020/6/21 12:28
 * @Version V1.0
 */
@Mapper
@Repository
public interface UserDao {

    User queryByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
