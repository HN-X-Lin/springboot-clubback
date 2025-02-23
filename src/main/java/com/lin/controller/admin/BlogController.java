package com.lin.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.pojo.Blog;
import com.lin.pojo.Tag;
import com.lin.pojo.Type;
import com.lin.pojo.User;
import com.lin.service.BlogService;
import com.lin.service.TagService;
import com.lin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String blogs(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,HttpSession session, Model model){
        PageHelper.startPage(pagenum, 5);
        User user = (User)session.getAttribute("user");
        List<Blog> allBlog = blogService.getAllBlog(user.getId());
//        System.out.println(allBlog.get(0));
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
    public String searchBlogs(HttpSession session, Blog blog, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        //System.out.println(blog);
        User user = (User)session.getAttribute("user");
        List<Blog> allBlog = blogService.searchBlog(blog,user.getId());
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("message", "查询成功");
        setTypeAndTag(model);
        return "admin/blogs";
    }

    /**
     * @title addblog
     * @description  blog新增和编辑
     * @param blog
     * @param session
     * @param attributes
     * @return java.lang.String
     * @author lizhuo
     * @updateTime 2020/7/4 11:37
     */
    @PostMapping("/blogs") //新增、编辑博客
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes){

        Date date = new Date();
        blog.setUpdateTime(date);

        if(blog.getId()==null){
            blog.setUser((User) session.getAttribute("user"));
            blog.setViews(0);
            blog.setCreateTime(date);
            blog.setTypeId(blog.getType().getId());
            blog.setUserId(blog.getUser().getId());
            blogService.saveBlog(blog);
            attributes.addFlashAttribute("msg", "新增成功");
        }else{
            blog.setTypeId(blog.getType().getId());
            System.out.println(blog);
            blogService.updateBlog(blog);
            attributes.addFlashAttribute("msg", "修改成功");
        }
        return "redirect:/admin/blogs";
    }

}
