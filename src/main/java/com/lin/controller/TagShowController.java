package com.lin.controller;

import com.github.pagehelper.PageInfo;
import com.lin.pojo.Blog;
import com.lin.pojo.Tag;
import com.lin.pojo.Type;
import com.lin.service.BlogService;
import com.lin.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @ClassName TagShowController
 * @Description
 * @Author lizhuo
 * @Date 2020/7/5 11:03
 * @Version V1.0
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("tags/{id}")
    public String tags(@PathVariable Long id, Model model){
        List<Tag> tags = tagService.getAllTag();

        if(id == -1) {
            id=tags.get(0).getId();
        }
        for(Tag tag : tags){
            tag.setBlogs(blogService.getByTagId(tag.getId(),tag.getName()));//查询有该标签的所有blog
            if(id == tag.getId()){
                PageInfo<Blog> pageInfo = new PageInfo<>(tag.getBlogs());
                model.addAttribute("pageInfo", pageInfo);
            }
            //System.out.println("show " + tag);
        }

//        List<Blog> blogs = blogService.getByTagId(id,tagService.getTag(id).getName());
//
//        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
//        System.out.println(111111);
//        System.out.println(id+tagService.getTag(id).getName());
//        System.out.println(blogs);
//        System.out.println(111111);
//        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", tags);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
