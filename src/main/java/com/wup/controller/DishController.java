package com.wup.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.wup.common.PageBean;
import com.wup.common.Result;
import com.wup.dto.DishDto;
import com.wup.entity.Dish;
import com.wup.entity.DishFlavor;
import com.wup.service.DishFlavorService;
import com.wup.service.DishService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @GetMapping("/page")
    public Result<PageBean<Dish>> list(@RequestParam("page") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize) {
        PageBean<Dish> pageBean = dishService.list(pageNum, pageSize);
        return Result.success(pageBean);
    }
    @Transactional
    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto) {
        Long operateUser = (Long) httpServletRequest.getSession().getAttribute("id");
        //补全dish数据
        //dishID给dish设置id，给对应的口味设置对应的dishId
        Long dishId = IdUtil.getSnowflakeNextId();
        dishDto.setId(dishId);
        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setCreateUser(operateUser);
        dishDto.setUpdateUser(operateUser);
        //补全dishFlavors数据,并写入数据库
        completeAndCommitEachFlavorFromDishDto(dishDto, dishId, operateUser);
        //log.info(dishDto.toString());
        dishService.save(dishDto);
        return Result.success("菜品添加成功");
    }
    @GetMapping("/{id}")
    public Result<DishDto> getDishDtoById(@PathVariable Long id) {
        Dish dish = dishService.findById(id);
        List<DishFlavor> dishFlavors = dishFlavorService.findFlavorsByDishId(id);
        DishDto dishDto = BeanUtil.copyProperties(dish, DishDto.class);
        dishDto.setFlavors(dishFlavors);
        return Result.success(dishDto);
    }
    @Transactional
    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto) throws Exception{
        //修改dish,由于name是主键，直接修改会报错，先删除对应的dish数据新增，id可以保留，再生成也可以
        //id不进行修改,需要获取id进行数据库的增删，修改也可以
        Long dishId = dishDto.getId();
        //删除dish，以及dish对应的flavors
        dishService.delete(dishId);
        dishFlavorService.deleteByDishId(dishId);
        //新增dish
        //补全dish数据
        dishDto.setUpdateTime(LocalDateTime.now());
        //本次请求的请求对象id
        Long operateUser = (Long) httpServletRequest.getSession().getAttribute("id");
        dishDto.setUpdateUser(operateUser);
        dishService.save(dishDto);
        //将请求的新的flavor写入数据库
        completeAndCommitEachFlavorFromDishDto(dishDto, dishId, operateUser);
        return Result.success(null);
    }

    /**
     * 将DishDto中的flavors列表中的每一个Flavor的数据进行补全，并写入数据库
     * @param dishDto
     * @param dishId
     * @param operateUser
     */
    @Transactional
    public void completeAndCommitEachFlavorFromDishDto(@RequestBody DishDto dishDto, Long dishId, Long operateUser) {
        dishDto.getFlavors().forEach((flavor) -> {
            flavor.setDishId(dishId);
            flavor.setId(IdUtil.getSnowflakeNextId());
            flavor.setCreateTime(LocalDateTime.now());
            flavor.setUpdateTime(LocalDateTime.now());
            flavor.setCreateUser(operateUser);
            flavor.setUpdateUser(operateUser);

            dishFlavorService.save(flavor);
        });
    }
}
