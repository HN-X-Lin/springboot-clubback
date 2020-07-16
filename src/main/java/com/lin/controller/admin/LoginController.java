package com.lin.controller.admin;

import com.lin.pojo.User;
import com.lin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginController
 * @Description
 * @Author xiaolin
 * @Date 2020/6/21 12:48
 * @Version V1.0
 */
@Controller
@RequestMapping("/admin")
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
    @GetMapping({"","/toLogin"})
    public String loginPage() {

            return "admin/login";

    }

    /**
     * @title login
     * @description 登陆请求
     * @return java.lang.String
     * @author xiaolin
     * @updateTime 2020/6/21 12:53
     */
    @PostMapping("/login")
    public String login(String username, String password, Model model,HttpSession session){
        //获取当前认证
        Subject subject = SecurityUtils.getSubject();

        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);//执行登录方法，如果没有异常就ok
            User user = (User) subject.getPrincipal();
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }catch (UnknownAccountException e){ //用户名
            model.addAttribute("msg", "用户名错误或密码错误");
            return "admin/login";
        }catch (IncorrectCredentialsException e){ //密码
            model.addAttribute("msg", "用户名错误或密码错误");
            return "admin/login";
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
//    @GetMapping("/logout")
//    public String logout(HttpSession session){
//        session.removeAttribute("user");
//        return "redirect:/admin";
//    }
}
