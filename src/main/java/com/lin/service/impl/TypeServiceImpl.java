package com.lin.service.impl;

import com.lin.dao.TypeDao;
import com.lin.pojo.Type;
import com.lin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TypeServiceImpl
 * @Description
 * @Author xiaolin
 * @Date 2020/6/24 16:02
 * @Version V1.0
 */
@Service
public class TypeServiceImpl implements TypeService {



    @Autowired
    private TypeDao typeDao;

    @Override
    public List<Type> getBlogType() {
        return typeDao.getBlogType();
    }

    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Override
    public Type getType(Long id) {
        return typeDao.getType(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Override
    public List<Type> getAllType() {
        return typeDao.getAllType();
    }

    @Override
    public int updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Override
    public int deleteType(Long id) {
        return typeDao.deleteType(id);
    }
}
