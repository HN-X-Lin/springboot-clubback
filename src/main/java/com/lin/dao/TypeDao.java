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

    /**
     * @title saveType
     * @description 新增一个type
     * @param type
     * @return com.lin.pojo.Type
     * @author xiaolin
     * @updateTime 2020/6/27 13:37
     */
    int saveType(Type type);

    /**
     * @title getType
     * @description  通过id查询
     * @param id
     * @return com.lin.pojo.Type
     * @author xiaolin
     * @updateTime 2020/6/27 13:38
     */
    Type getType(Long id);

    /**
     * @title getTypeByName
     * @description  通过name获得
     * @param name
     * @return com.lin.pojo.Type
     * @author xiaolin
     * @updateTime 2020/6/27 13:38
     */
    Type getTypeByName(String name);

    /**
     * @title getAllType
     * @description 查询全部类型
     * @param
     * @return java.util.List<com.lin.pojo.Type>
     * @author xiaolin
     * @updateTime 2020/6/26 16:37
     */
    List<Type> getAllType();

    /**
     * @title updateType
     * @description 更新一个type
     * @param type
     * @return int
     * @author xiaolin
     * @updateTime 2020/6/27 13:39
     */
    int updateType(Type type); //更新

    /**
     * @title deleteType
     * @description 删除一个type
     * @param id
     * @return int
     * @author xiaolin
     * @updateTime 2020/6/27 13:39
     */
    int deleteType(Long id); //删除
}

