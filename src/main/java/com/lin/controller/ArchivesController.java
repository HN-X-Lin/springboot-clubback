package com.lin.controller;

import com.lin.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName ArchivesController
 * @Description 时间线页面
 * @Author xiaolin
 * @Date 2020/7/4 20:31
 * @Version V1.0
 */
@Controller
public class ArchivesController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){

        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("archiveCount",blogService.countBlog());

        return "archives";
    }
}
