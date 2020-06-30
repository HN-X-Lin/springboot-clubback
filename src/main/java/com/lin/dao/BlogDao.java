package com.lin.dao;

import com.lin.pojo.Blog;
import com.lin.pojo.BlogAndTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaolin
 * @version 1.0
 * @ClassName BlogDao
 * @Description
 * @date 2020/6/22 20:23
 */
@Mapper
@Repository
public interface BlogDao {

    Blog getBlog(Long id);//通过id 获得blog

    List<Blog> getAllBlog();//查询所有博客

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteBlog(Long id);

    List<Blog> searchBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客

    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客

    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客

    int saveBlogAndTag(BlogAndTag blogAndTag);
}
