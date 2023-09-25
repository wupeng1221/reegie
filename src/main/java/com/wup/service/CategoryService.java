package com.wup.service;

import com.wup.common.PageBean;
import com.wup.entity.Category;
import com.wup.entity.Employee;

import java.util.List;

public interface CategoryService {
    PageBean<Category> list(Integer pageNum, Integer pageSize);
    void save(Category category);
    void delete(Long id);
    void update(Category category);
    List<Category> findByType(Integer type);
}
