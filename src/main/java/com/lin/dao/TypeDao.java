package com.lin.dao;

import com.lin.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaolin
 * @version 1.0
 * @ClassName TypeDao
 * @Description
 * @date 2020/6/22 20:24
 */
@Mapper
@Repository
public interface TypeDao {

    Type saveType(Type type); //新增

    Type getType(Long id);//通过id获得

    Type getTypeByName(String name);//通过name获得

    List<Type> getAllType();//查询全部

    int updateType(Type type); //更新

    int deleteType(Long id); //删除
}

