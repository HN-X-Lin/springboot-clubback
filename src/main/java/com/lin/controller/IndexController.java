package com.lin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName IndexContreller
 * @Description
 * @Author xiaolin
 * @Date 2020/6/21 12:52
 * @Version V1.0
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
