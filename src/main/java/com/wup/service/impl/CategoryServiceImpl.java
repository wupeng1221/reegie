package com.wup.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wup.common.PageBean;
import com.wup.entity.Category;
import com.wup.entity.Employee;
import com.wup.mapper.CategoryMapper;
import com.wup.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Override
    public PageBean<Category> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> categories = categoryMapper.list();
        PageInfo<Category> pageInfo = new PageInfo<>(categories);
        return new PageBean<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void save(Category category) {
        category.setId(IdUtil.getSnowflakeNextId());
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Long userId = (Long) httpServletRequest.getSession().getAttribute("id");
        category.setCreateUser(userId);
        category.setUpdateUser(userId);
        categoryMapper.save(category);
    }

    @Override
    public void delete(Long id) {
        // TODO 还需要查看被删除的分类是否关联了菜品dish和套餐meal，如果有关联则返回错误
        categoryMapper.delete(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser((Long) httpServletRequest.getSession().getAttribute("id"));
        categoryMapper.update(category);
    }

    @Override
    public List<Category> findByType(Integer type) {
        return categoryMapper.findAllByType(type);
    }
}
