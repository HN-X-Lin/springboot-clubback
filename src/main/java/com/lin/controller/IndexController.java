package com.lin.controller;

import com.lin.pojo.Blog;
import com.lin.pojo.Type;
import com.lin.service.BlogService;
import com.lin.service.TagService;
import com.lin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String index(Model model){

        List<Type> allType = typeService.getBlogType();
        for(Type type : allType){
            System.out.println("index " + type);
        }
        model.addAttribute("types", allType);
        return "index";
    }


    @GetMapping("/blog/{id}")
    public String blogs(@PathVariable Long id,Model model){

        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog", blog);

        return "blog";
    }

}
