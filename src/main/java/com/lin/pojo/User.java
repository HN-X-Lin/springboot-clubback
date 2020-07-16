package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName User
 * @Description
 * @Author xiaolin
 * @Date 2020/6/19 12:22
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;//用户id
    private String nickname;//用户昵称
    private String username;//账号
    private String password;//密码
    private String email;//邮箱
    private String avatar;//头像图片
    private String roles; //角色权限
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    private List<Blog> blogs = new ArrayList<>();
}