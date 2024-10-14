package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Like
 * @Description 点赞对象实体
 * @Author lizhuo
 * @Date 2020/7/18 16:53
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private Long id;
    private Long blog_id;//博客id
    private Long user_id;//用户id
}
