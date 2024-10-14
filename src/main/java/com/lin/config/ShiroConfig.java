package com.lin.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ShiroConfig
 * @Description
 * @Author xiaolin
 * @Date 2020/7/13 22:43
 * @Version V1.0
 */
@Configuration
public class ShiroConfig {

    //ShiroFilerFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /**
         * anon 无需认证可以访问
         * authc 必须认证才能访问
         * user 必须拥有 记住我 这个功能才能用
         * perms 拥有某个资源的权限才能访问
         * role 拥有某个角色权限才能访问
         */
        Map<String ,String > filerMap = new LinkedHashMap<>();

        //认证
        filerMap.put("/*", "anon"); //都可以访问
        filerMap.put("/user/**", "anon");//都可以访问
        filerMap.put("/admin/login", "anon");//都可以访问
        filerMap.put("/admin/toLogin", "anon");//都可以访问
        filerMap.put("/admin/blogs/**", "authc");//认证后可以访问
        filerMap.put("/admin/index", "authc");//认证后可以访问

        //授权
        filerMap.put("/admin/types/**", "roles[ADMIN]"); //需要admin权限
        filerMap.put("/admin/tags/**", "roles[ADMIN]");//需要admin权限

        filerMap.put("/admin/logout", "logout");
        bean.setFilterChainDefinitionMap(filerMap);
        //设置没权限去登陆
        bean.setLoginUrl("/admin/toLogin");

        return bean;
    }

    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象
    @Bean(name = "userRealm")
    public UserRealm userRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setAuthorizationCachingEnabled(false);
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    //整合shiro thymeleaf  ShiroDialect
    @Bean(name = "shiroDialect")
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


}
