package com.wup.service;

import com.wup.entity.Dish;
import com.wup.entity.DishFlavor;

import java.util.List;

/**
* @author brainwu
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service
* @createDate 2023-09-25 13:39:48
*/
public interface DishFlavorService {
    void save(DishFlavor dishFlavor);
    List<DishFlavor> findFlavorsByDishId(Long dishId);
    void deleteByDishId(Long dishId);
}
