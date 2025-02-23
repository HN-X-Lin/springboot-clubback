package com.lin.service;

import com.lin.pojo.Tag;

import java.util.List;

/**
 * @ClassName TagService
 * @Description
 * @Author xiaolin lizhuo
 * @Date 2020/6/26 17:18
 * @Version V2.0
 */
public interface TagService {

    int saveTag(Tag tag); //新增

    Tag getTag(Long id); //通过id获得

    Tag getTagByName(String name); //通过name获得

    List<Tag> getAllTag(); //获得全部

    List<Tag> getTagByString(String text);   //从字符串中获取tag集合

    //List<Tag> getBlogTag();

    int updateTag(Tag tag); //更新标签

    int deleteTag(Long id);//删除标签



}
