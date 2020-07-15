package com.lin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.pojo.Blog;
import com.lin.pojo.Type;
import com.lin.service.BlogService;
import com.lin.service.TypeService;
import com.lin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName TypeShowController
 * @Description
 * @Author xiaolin
 * @Date 2020/7/3 16:45
 * @Version V1.0
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("types/{id}")
    public String types(@PathVariable Long id, Model model){
        List<Type> types = typeService.getBlogType();
        for(Type type : types){
            System.out.println("show " + type);
        }
        if(id == -1){
            id = types.get(0).getId();
        }
        List<Blog> blogs = blogService.getByTypeId(id);
        for(Blog blog: blogs){
            String key = "blog"+blog.getId();
            if(redisUtil.get(key) != null){//获取redis中的views，mysql中的可能没更新
                blog.setViews((Integer) redisUtil.get(key));
            }else{
                redisUtil.set(key,blog.getViews());
            }
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types", types);
        model.addAttribute("activeTypeId", id);

        return "types";
    }


}
