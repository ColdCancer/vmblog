package com.demo.controller;

import com.demo.entity.ErPosition;
import com.demo.service.ErPositionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ErPosition)表控制层
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@RestController
@RequestMapping("erPosition")
public class ErPositionController {
    /**
     * 服务对象
     */
    @Resource
    private ErPositionService erPositionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ErPosition selectOne(Integer id) {
        return this.erPositionService.queryById(id);
    }

}