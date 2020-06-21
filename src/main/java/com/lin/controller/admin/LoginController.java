package com.lin.controller.admin;

import com.lin.pojo.User;
import com.lin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginController
 * @Description
 * @Author xiaolin
 * @Date 2020/6/21 12:48
 * @Version V1.0
 */
@RequestMapping("/admin")
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * @title loginPage
     * @description 首页映射
     * @param 
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/21 12:54
     */
    @GetMapping()
    public String loginPage() {
        return "admin/login";
    }

    /**
     * @title login
     * @description 登陆请求
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/21 12:53
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * @title logout
     * @description  登出
     * @param session
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/21 12:53
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
