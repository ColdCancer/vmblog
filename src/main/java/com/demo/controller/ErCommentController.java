package com.demo.controller;

import com.demo.entity.ErComment;
import com.demo.service.ErCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ErComment)表控制层
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@RestController
@RequestMapping("erComment")
public class ErCommentController {
    /**
     * 服务对象
     */
    @Resource
    private ErCommentService erCommentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ErComment selectOne(Integer id) {
        return this.erCommentService.queryById(id);
    }

}