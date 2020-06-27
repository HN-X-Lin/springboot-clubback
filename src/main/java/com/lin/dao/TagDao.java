package com.lin.dao;

import com.lin.pojo.Tag;
import com.lin.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaolin
 * @version 1.0
 * @ClassName TagDao
 * @Description
 * @date 2020/6/22 20:24
 */
/**
 * @title TagDao
 * @description
 * @return 
 * @author lizhuo
 * @updateTime 2020/6/26 17:16
 */
@Mapper
@Repository
public interface TagDao {
    int saveTag(Tag tag); //新增

    Tag getTag(Long id);//通过id获得

    Tag getTagByName(String name);//通过name获得

    List<Tag> getAllTag();//查询全部

    int updateTag(Tag tag); //更新

    int deleteTag(Long id); //删除
}
