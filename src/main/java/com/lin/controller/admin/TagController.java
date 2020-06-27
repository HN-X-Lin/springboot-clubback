package com.lin.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @ClassName TagController
 * @Description
 * @Author xiaolin
 * @Date 2020/6/26 11:59
 * @Version V1.0
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @GetMapping("/tags")
    public String tags(){

        return "admin/tags";
    }

}
