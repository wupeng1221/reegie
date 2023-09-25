package com.wup.mapper;

import com.wup.entity.Category;
import com.wup.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author brainwu
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2023-09-23 14:54:26
* @Entity com.wup.entity.Category
*/
@Mapper
public interface CategoryMapper {
    List<Category> list();
    void save(Category category);
    void delete(Long id);
    void update(Category category);
    List<Category> findAllByType(Integer type);
}




