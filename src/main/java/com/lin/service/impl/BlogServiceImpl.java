package com.lin.service.impl;

import com.lin.dao.BlogDao;
import com.lin.exception.NotFoundException;
import com.lin.pojo.Blog;
import com.lin.pojo.BlogAndTag;
import com.lin.pojo.Tag;
import com.lin.service.BlogService;
import com.lin.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName BlogServiceImpl
 * @Description
 * @Author xiaolin
 * @Date 2020/6/23 0:02
 * @Version V1.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;


    @Override
    public Blog getBlog(Long id) {
        return blogDao.getBlog(id);
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogDao.getAllBlog();
    }

    /**
     * @title saveBlog
     * @description 后台新增blog
     * @param blog
     * @return int
     * @author xiaolin
     * @updateTime 2020/7/5 23:58
     */
    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //保存博客
        int flag = blogDao.saveBlog(blog);
        //保存博客后才能获取自增的id
        Long id = blog.getId();
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            //新增时无法获取自增的id,在mybatis里修改
            blogAndTag = new BlogAndTag(tag.getId(), id);
            blogDao.saveBlogAndTag(blogAndTag);
        }
        return flag;
    }

    /**
     * @title updateBlog
     * @description 后台更新blog 
     * @param blog
     * @return int
     * @author xiaolin
     * @updateTime 2020/7/5 23:59
     */
    @Override
    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            blogAndTag = new BlogAndTag(tag.getId(), blog.getId());
            blogDao.saveBlogAndTag(blogAndTag);
        }
        return blogDao.updateBlog(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        return blogDao.deleteBlog(id);
    }

    @Override
    public List<Blog> searchBlog(Blog blog) {
        return blogDao.searchBlog(blog);
    }

    /**
     * @title archiveBlog
     * @description 按年份归档blog  
     * @param 
     * @return java.util.Map<java.lang.String,java.util.List<com.lin.pojo.Blog>>
     * @author xiaolin
     * @updateTime 2020/7/6 0:01
     */
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        
        List<String> years = blogDao.findGroupYear();
        Set<String> set = new HashSet<>(years);  //set去掉重复的年份

        TreeMap<String, List<Blog>>map = new TreeMap<String, List<Blog>>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        for (String year : set) {
            System.out.println(year);
            map.put(year, blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public int countBlog() {
        return blogDao.getAllBlog().size();
    }

    @Override
    public List<Blog> getByTypeId(Long typeId) {
        return blogDao.getByTypeId(typeId);
    }

    @Override
    public List<Blog> getByTagId(Long tagId,String tagName) {
        return blogDao.getByTagId(tagId,tagName);
    }

    @Override
    public Blog getDetailedBlog(Long id) {
        Blog blog = blogDao.getDetailedBlog(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将Markdown格式转换成html
        return blog;
    }

    @Override
    public List<Blog> getIndexBlog() {
        return blogDao.getIndexBlog();
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogDao.getAllRecommendBlog();
    }

    @Override
    public List<Blog> getSearchBlog(String info) {
        return blogDao.getSearchBlog(info);
    }

    @Override
    public  int updateBlogType(Long typeId,Long blogId){
        return blogDao.updateBlogType(typeId,blogId);
    }

    @Override
    public int updateBlogTag(Blog blog){
        return blogDao.updateBlogTag(blog);
    }

    @Override
    public  int deleteBlogId_TagId(Long tag_id){
        return blogDao.deleteBlogId_TagId(tag_id);
    }

    @Override
    public int saveBlogId_TagId(Long blog_id,Long tag_id){
        return blogDao.saveBlogId_TagId(blog_id,tag_id);
    }

    @Override
    public int updateViews(Long blogId,Long views){
        return blogDao.updateViews(blogId,views);
    }
}
