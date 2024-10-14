package com.lin.service.impl;

import com.lin.dao.LikeDao;
import com.lin.pojo.Like;
import com.lin.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName LikeServiceImpl
 * @Description
 * @Author lizhuo
 * @Date 2020/8/4 10:59
 * @Version V1.0
 */
@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
     private LikeDao likeDao;

    @Override
    public Like getLike(Long uid,Long bid){
        return likeDao.getLike(uid, bid);
    }


}
