package com.lin.service.impl;

import com.lin.pojo.Tag;
import com.lin.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TagServiceImpl
 * @Description
 * @Author xiaolin
 * @Date 2020/6/24 16:01
 * @Version V1.0
 */
@Service
public class TagServiceImpl implements TagService {
    @Override
    public int saveTag(Tag tag) {
        return 0;
    }

    @Override
    public Tag getTag(Long id) {
        return null;
    }

    @Override
    public Tag getTagByName(String name) {
        return null;
    }

    @Override
    public List<Tag> getAllTag() {
        return null;
    }

    @Override
    public List<Tag> getTagByString(String text) {
        return null;
    }

    @Override
    public int updateTag(Tag tag) {
        return 0;
    }

    @Override
    public int deleteTag(Long id) {
        return 0;
    }
}
