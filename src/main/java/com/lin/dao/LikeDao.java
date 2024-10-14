package com.lin.dao;

import com.lin.pojo.Like;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName LikeDao
 * @Description 点赞对象
 * @Author lizhuo
 * @Date 2020/8/4 11:04
 * @Version V1.0
 */
@Mapper
@Repository
public interface LikeDao {
    Like getLike(Long uid,Long bid);//获取uid用户对bid博客的点赞信息
}
