package com.demo.controller;

import com.demo.entity.ArticleSave;
import com.demo.service.ArticleSaveService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ArticleSave)表控制层
 *
 * @author makejava
 * @since 2022-04-22 08:11:48
 */
@RestController
@RequestMapping("articleSave")
public class ArticleSaveController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleSaveService articleSaveService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ArticleSave selectOne(Integer id) {
        return this.articleSaveService.queryById(id);
    }

}