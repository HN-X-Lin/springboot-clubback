package com.lin.service;


import com.lin.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lenovo
 * @version 1.0
 * @ClassName TypeService
 * @Description
 * @date 2020/6/23 0:03
 */

public interface TypeService {

    Type saveType(Type type); //新增

    Type getType(Long id);//通过id获得

    Type getTypeByName(String name);//通过name获得

    List<Type> getAllType();//查询全部

    //List<Type> getBlogType();  //首页右侧展示type对应的博客数量

    int updateType(Type type); //更新

    int deleteType(Long id); //删除

}
