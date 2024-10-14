//package com.lin.interceptor;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
///**
// * @ClassName LoginInterceptor
// * @Description 登录拦截器
// * @Author xiaolin
// * @Date 2020/6/19 14:14
// * @Version V1.0
// */
//public class LoginInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (request.getSession().getAttribute("user") == null){
//            response.sendRedirect("/admin");
//            return false;
//        }
//        return true;
//    }
//}