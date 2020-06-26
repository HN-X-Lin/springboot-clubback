package com.lin.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.pojo.Tag;
import com.lin.pojo.Type;
import com.lin.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName TagController
 * @Description
 * @Author xiaolin
 * @Date 2020/6/26 11:59
 * @Version V1.0
 */

/**
 * @title TagController
 * @description
 * @return 
 * @author lizhuo
 * @updateTime 2020/6/26 17:19
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private  TagService tagService;
    @GetMapping("/tags")
    public String tags(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum,5);
        List<Tag> allTag = tagService.getAllTag();

        PageInfo<Tag> pageInfo = new PageInfo<>(allTag);
        model.addAttribute("pageInfo",pageInfo );
        return "admin/tags";
    }

}
