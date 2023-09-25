package com.wup.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wup.common.PageBean;
import com.wup.dto.DishDto;
import com.wup.entity.Dish;
import com.wup.mapper.DishMapper;
import com.wup.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Override
    public PageBean<Dish> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dish> dishes = dishMapper.list();
        PageInfo<Dish> pageInfo = new PageInfo<>(dishes);
        return new PageBean<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void save(DishDto dishDto) {
        dishMapper.save(dishDto);
    }

    @Override
    public Dish findById(Long id) {
        return dishMapper.findById(id);
    }

    @Override
    public void delete(Long id) {
        dishMapper.deleteById(id);
    }
}
