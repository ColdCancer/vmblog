package com.demo.controller;

import com.demo.entity.ErDevelopment;
import com.demo.service.ErDevelopmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ErDevelopment)表控制层
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@RestController
@RequestMapping("erDevelopment")
public class ErDevelopmentController {
    /**
     * 服务对象
     */
    @Resource
    private ErDevelopmentService erDevelopmentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ErDevelopment selectOne(Integer id) {
        return this.erDevelopmentService.queryById(id);
    }

}