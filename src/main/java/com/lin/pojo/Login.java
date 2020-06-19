package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName Login
 * @Description
 * @Author xiaolin
 * @Date 2020/6/19 12:00
 * @Version V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    private Date time;//最后登录时间
    private String ip;//最后登录ip
    private User user;//用户

}
