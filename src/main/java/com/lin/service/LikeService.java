package com.lin.service;

import com.lin.pojo.Like;

/**
 * @ClassName LikeService
 * @Description
 * @Author lizhuo
 * @Date 2020/8/4 11:00
 * @Version V1.0
 */
public interface LikeService {
    Like getLike(Long uid,Long bid);//获取uid用户对bid博客的点赞信息
}
