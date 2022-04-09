package com.demo.controller;

import com.demo.entity.ErResource;
import com.demo.service.ErResourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ErResource)表控制层
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@RestController
@RequestMapping("erResource")
public class ErResourceController {
    /**
     * 服务对象
     */
    @Resource
    private ErResourceService erResourceService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ErResource selectOne(Integer id) {
        return this.erResourceService.queryById(id);
    }

}