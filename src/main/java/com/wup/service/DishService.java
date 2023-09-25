package com.wup.service;


import com.wup.common.PageBean;
import com.wup.dto.DishDto;
import com.wup.entity.Dish;

public interface DishService {
    PageBean<Dish> list(Integer pageNum, Integer pageSize);
    void save(DishDto dishDto);
    Dish findById(Long id);
    void delete(Long id);
}
