package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName User
 * @Description
 * @Author xiaolin
 * @Date 2020/6/19 11:58
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;     //用户id
    private String name; //用户名
    private String password; //用户密码
    private String mail; //邮箱
    private Integer state;//状态
    private List<Role> roles; //角色
    private Login login; //登陆情况

}
