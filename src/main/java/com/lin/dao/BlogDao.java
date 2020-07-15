package com.lin.dao;

import com.lin.pojo.Blog;
import com.lin.pojo.BlogAndTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    int updateBlogTag(Blog blog);

    int updateBlogType(Long typeId,Long blogId);

    int updateViews(@Param("blogId") Long blogId,@Param("views") Long views);

    int deleteBlog(Long id);

    int deleteBlogId_TagId(@Param("tag_id") Long tag_id);

    int saveBlogId_TagId(@Param("blog_id")Long blog_id,@Param("tag_id")Long tag_id);

    List<Blog> searchBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客

    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客

    List<Blog> getByTagId(Long tagId,String tagName);  //根据标签id获取博客,tagName用来在数据库查询的时候去重

    int saveBlogAndTag(BlogAndTag blogAndTag);

    List<Blog> findByYear(@Param("year") String year);  //按年份查询归档博客

    List<String> findGroupYear();//已有年份的集合

    Blog getDetailedBlog(Long id);//获取blog全部

    List<Blog> getIndexBlog();//首页blog显示

    List<Blog> getAllRecommendBlog();//获得推荐blog

    List<Blog> getSearchBlog(@Param("info") String info);//全站搜索


}
