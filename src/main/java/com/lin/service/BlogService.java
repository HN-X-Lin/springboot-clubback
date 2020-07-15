package com.lin.service;

import com.lin.pojo.Blog;

import java.util.List;
import java.util.Map;

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

    int updateBlogType(Long typeId,Long blogId);

    int updateBlogTag(Blog blog);

    int updateViews(Long blogId,Long views);

    int deleteBlogId_TagId(Long tag_id);

    int saveBlogId_TagId(Long blog_id,Long tag_id);

    int deleteBlog(Long id);

    List<Blog> searchBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客

    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客

    List<Blog> getByTagId(Long tagId,String tagName);  //根据标签id获取博客

    Map<String,List<Blog> > archiveBlog();//根据时间归档blog

    int countBlog();//查询所有blog条目

    Blog getDetailedBlog(Long id); // blog详细页

    List<Blog> getIndexBlog();//首页blog展示

    List<Blog> getAllRecommendBlog();//首页推荐blog展示

    List<Blog> getSearchBlog(String info);

}
