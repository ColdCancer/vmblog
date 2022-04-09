package com.demo.controller;

import com.demo.entity.GlobalChat;
import com.demo.service.GlobalChatService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (GlobalChat)表控制层
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@RestController
@RequestMapping("globalChat")
public class GlobalChatController {
    /**
     * 服务对象
     */
    @Resource
    private GlobalChatService globalChatService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public GlobalChat selectOne(Integer id) {
        return this.globalChatService.queryById(id);
    }

}