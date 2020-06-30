package com.lin.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.pojo.Blog;
import com.lin.service.BlogService;
import com.lin.service.TagService;
import com.lin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @ClassName BlogController
 * @Description
 * @Author xiaolin
 * @Date 2020/6/24 17:10
 * @Version V1.0
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * @title setTypeAndTag
     * @description type 和 tag 的初始化
     * @param model
     * @return void
     * @author xiaolin
     * @updateTime 2020/6/30 13:30
     */
    public void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
    }

    /**
     * @title blogs
     * @description  后台展示全部 blog 部分信息
     * @param pagenum
     * @param model
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/30 13:14
     */
    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Blog> allBlog = blogService.getAllBlog();
        System.out.println(allBlog.get(0));
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);  //查询类型和标签
        return "admin/blogs";
    }

    /**
     * @title toAddBlog
     * @description  去新增博客页面
     * @param model
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/30 13:34
     */
    @GetMapping("/blogs/input")
    public String toAddBlog(Model model){
        model.addAttribute("blog", new Blog());  //返回一个blog对象给前端th:object
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    /**
     * @title toEditBlog
     * @description  进入blog编辑页面
     * @param id
     * @param model
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/30 13:34
     */
    @GetMapping("/blogs/{id}/input")
    public String toEditBlog(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlog(id);
        blog.init();   //将tags集合转换为tagIds字符串
        model.addAttribute("blog", blog);     //返回一个blog对象给前端th:object
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    /**
     * @title deleteBlogs
     * @description 删除指定博客
     * @param id
     * @param attributes
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/30 13:33
     */
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlogs(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/blogs";
    }

    /**
     * @title searchBlogs
     * @description  查询相对应的blog
     * @param blog
     * @param pagenum
     * @param model
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/30 18:58
     */
    @PostMapping("/blogs/search")  //按条件查询博客
    public String searchBlogs(Blog blog, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        System.out.println(blog);
        List<Blog> allBlog = blogService.searchBlog(blog);
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("message", "查询成功");
        setTypeAndTag(model);
        return "admin/blogs";
    }
}
