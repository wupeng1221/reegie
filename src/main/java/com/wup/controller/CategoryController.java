package com.wup.controller;

import com.wup.common.PageBean;
import com.wup.common.Result;
import com.wup.entity.Category;
import com.wup.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/page")
    public Result<PageBean<Category>> list(@RequestParam("page") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize) {
        PageBean<Category> pageBean = categoryService.list(pageNum, pageSize);
        return Result.success(pageBean);
    }

    @PostMapping
    public Result<Object> save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success(null);
    }

    @DeleteMapping()
    public Result<Object> delete(@RequestParam("ids") Long id) {
        categoryService.delete(id);
        return Result.success(null);
    }
    @PutMapping
    public Result<Object> update(@RequestBody Category category) {
        categoryService.update(category);
        return Result.success(null);
    }
    @GetMapping("/list")
    public Result<List<Category>> listByType(
            @RequestParam(name = "type", defaultValue = "1")
            Integer type) {
        List<Category> categories = categoryService.findByType(type);
        log.info(categories.toString());
        return Result.success(categories);
    }
}
