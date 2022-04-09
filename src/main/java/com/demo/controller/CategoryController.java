package com.demo.controller;

import com.demo.entity.Category;
import com.demo.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Category)表控制层
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Category selectOne(Integer id) {
        return this.categoryService.queryById(id);
    }

}