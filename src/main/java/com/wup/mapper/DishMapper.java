package com.wup.mapper;

import com.wup.dto.DishDto;
import com.wup.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author brainwu
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2023-09-23 23:23:23
* @Entity com.wup.entity.Dish
*/
@Mapper
public interface DishMapper {
    List<Dish> list();
    void save(DishDto dishDto);
    Dish findById(Long id);
    void update(Dish dish);
    void deleteById(Long id);
}




