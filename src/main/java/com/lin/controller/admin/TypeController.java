package com.lin.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.pojo.Blog;
import com.lin.pojo.Type;
import com.lin.service.BlogService;
import com.lin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @ClassName TypeController
 * @Description
 * @Author xiaolin
 * @Date 2020/6/26 11:59
 * @Version V1.0
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    /**
     * @title types
     * @description 展示types
     * @param pagenum 页面
     * @param model 模块
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/26 17:11
     */
    @GetMapping("/types")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum,5);
        List<Type> allType = typeService.getAllType();

        PageInfo<Type> pageInfo = new PageInfo<>(allType);
        model.addAttribute("pageInfo",pageInfo );
        return "admin/types";
    }
    /**
     * @title toAddType
     * @description   进入新增页面
     * @param model
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/27 13:24
     */
    @GetMapping("/types/input")
    public String toAddType(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * @title toEditType
     * @description 进入 指定id的编辑页面
     * @param id
     * @param model
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/27 13:25
     */
    @GetMapping("/types/{id}/input")
    public String toEditType(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * @title addType
     * @description  新增一个type
     * @param type
     * @param attributes
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/27 15:10
     */
    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes){   
        Type t = typeService.getTypeByName(type.getName());
        if(t != null){
            attributes.addFlashAttribute("msg", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("msg", "添加成功");
        }
        typeService.saveType(type);
        return "redirect:/admin/types";   //不能直接跳转到types页面，否则不会显示type数据(没经过types方法)
    }

    /**
     * @title editType
     * @description  编辑自定id的type
     * @param id
     * @param type
     * @param attributes
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/27 15:10
     */
    @PostMapping("/types/{id}")
    public String editType(@PathVariable Long id, Type type, RedirectAttributes attributes){  //修改
        Type t = typeService.getTypeByName(type.getName());
        if(t != null){
            attributes.addFlashAttribute("msg", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("msg", "修改成功");
        }
        typeService.updateType(type);
        return "redirect:/admin/types";   //不能直接跳转到types页面，否则不会显示type数据(没经过types方法)
    }

    /**
     * @title delete
     * @description  删除指定id的type 
     * @param id
 * @param attributes
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/27 15:11
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        Type type=typeService.getType(id);
        type.setBlogs(blogService.getByTypeId(type.getId()));

        for(Blog blog: type.getBlogs()){//将原来属于这一分类的blog变为‘其它’分类
            System.out.println(blog);
            blogService.updateBlogType((long)0,blog.getId());
        }
        typeService.deleteType(id);

        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/types";
    }

}
