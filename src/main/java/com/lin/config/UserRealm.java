package com.lin.config;

import com.lin.pojo.User;
import com.lin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName UserRealm
 * @Description
 * @Author xiaolin
 * @Date 2020/7/13 10:33
 * @Version V1.0
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        /**不能用 principalCollection 来自doGetAuthenticationInfo 的返回第一个参数 整合thymeleaf 会报错
         * 要通过subject 获取 用户
        */
//        System.out.println( "授权 doGetAuthorizationInfo" + principalCollection);
//        System.out.println("Session   ++++" +subject.getSession());

        info.addRole(currentUser.getRoles());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        //连接数据库
        User user = userService.queryUser(userToken.getUsername());
        if(user==null){
            return null;
        }
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
        //密码认证 shiro 自己做 不需要
        return new SimpleAuthenticationInfo(user,user.getPassword(),credentialsSalt,getName());
    }
}
