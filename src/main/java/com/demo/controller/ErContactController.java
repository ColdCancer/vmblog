package com.demo.controller;

import com.demo.entity.ErContact;
import com.demo.service.ErContactService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ErContact)表控制层
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@RestController
@RequestMapping("erContact")
public class ErContactController {
    /**
     * 服务对象
     */
    @Resource
    private ErContactService erContactService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ErContact selectOne(Integer id) {
        return this.erContactService.queryById(id);
    }

}