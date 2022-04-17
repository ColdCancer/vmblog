package com.demo.controller;

import com.demo.entity.ArticleState;
import com.demo.service.ArticleStateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ArticleState)表控制层
 *
 * @author vmice
 * @since 2022-04-17 22:24:57
 */
@RestController
@RequestMapping("articleState")
public class ArticleStateController {
    @Resource
    private ArticleStateService articleStateService;

    @GetMapping("selectOne")
    public ArticleState selectOne(Integer id) {
        return this.articleStateService.queryById(id);
    }

}