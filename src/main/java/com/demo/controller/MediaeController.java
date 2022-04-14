package com.demo.controller;

import com.demo.entity.Mediae;
import com.demo.service.MediaeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Mediae)表控制层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@RestController
public class MediaeController {
    @Resource
    private MediaeService mediaeService;

    @GetMapping("selectOne")
    public Mediae selectOne(Integer id) {
        return this.mediaeService.queryById(id);
    }

}