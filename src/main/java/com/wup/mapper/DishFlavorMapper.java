package com.wup.mapper;

import com.wup.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author brainwu
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2023-09-25 13:39:48
* @Entity com.wup.entity.DishFlavor
*/
@Mapper
public interface DishFlavorMapper {
    void save(DishFlavor dishFlavor);
    List<DishFlavor> findByDishIdDishFlavor(Long dishId);
    void deleteByDishId(Long dishId);
}
