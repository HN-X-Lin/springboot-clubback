package com.lin.service;

import com.lin.pojo.Blog;

import java.util.List;

/**
 * @ClassName BlogService
 * @Description
 * @Author xiaolin
 * @Date 2020/6/24 12:44
 * @Version V1.0
 */
public interface BlogService {

    Blog getBlog(Long id);

    List<Blog> getAllBlog();//查询所有博客

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteBlog(Long id);

    List<Blog> searchBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客

    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客

    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客



}
