package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName Comment
 * @Description 评论
 * @Author xiaolin
 * @Date 2020/6/22 19:34
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id; //用户id
    private String nickname; //昵称
    private String email;  //邮箱
    private String content;  //评论内容
    private boolean adminComment;  //是否为管理员评论
    private String avatar; //头像路径
    private Date createTime; //创建时间
    private Long blogId; //
    private String parentNickname;



//    private Long parentCommentId;  //父评论id
//    private Comment parentComment; //父评论

    private Blog blog;

}
