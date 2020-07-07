package com.lin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.pojo.Blog;
import com.lin.pojo.Tag;
import com.lin.pojo.Type;
import com.lin.service.BlogService;
import com.lin.service.TagService;
import com.lin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import java.util.List;

/**
 * @ClassName IndexContreller
 * @Description
 * @Author xiaolin
 * @Date 2020/6/21 12:52
 * @Version V1.0
 */
@Controller
public class IndexController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String toIndex(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){

        PageHelper.startPage(pagenum, 8);
        List<Blog> allBlog = blogService.getIndexBlog();
        List<Type> allType = typeService.getBlogType();  //获取博客的类型(联表查询)

        List<Tag> allTag = tagService.getAllTag();
        for(Tag tag : allTag){
            tag.setBlogs(blogService.getByTagId(tag.getId(),tag.getName()));//查询有该标签的所有blog
        }
        List<Blog> recommendBlog =blogService.getAllRecommendBlog();  //获取推荐博客

        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendBlogs", recommendBlog);
        return "index";
    }


    @GetMapping("/blog/{id}")
    public String blogs(@PathVariable Long id,Model model){

        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog", blog);

        return "blog";
    }

}
