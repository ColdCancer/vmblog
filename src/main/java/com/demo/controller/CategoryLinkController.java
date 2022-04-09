package com.demo.controller;

import com.demo.entity.CategoryLink;
import com.demo.service.CategoryLinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (CategoryLink)表控制层
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@RestController
@RequestMapping("categoryLink")
public class CategoryLinkController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryLinkService categoryLinkService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public CategoryLink selectOne(Integer id) {
        return this.categoryLinkService.queryById(id);
    }

}