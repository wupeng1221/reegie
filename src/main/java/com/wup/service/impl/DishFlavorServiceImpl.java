package com.wup.service.impl;

import com.wup.entity.DishFlavor;
import com.wup.service.DishFlavorService;
import com.wup.mapper.DishFlavorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author brainwu
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-09-25 13:39:48
*/
@Service
public class DishFlavorServiceImpl implements DishFlavorService{
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Override
    public void save(DishFlavor dishFlavor) {
        dishFlavorMapper.save(dishFlavor);
    }

    @Override
    public List<DishFlavor> findFlavorsByDishId(Long dishId) {
        return dishFlavorMapper.findByDishIdDishFlavor(dishId);
    }

    @Override
    public void deleteByDishId(Long dishId) {
        dishFlavorMapper.deleteByDishId(dishId);
    }
}
