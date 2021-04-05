package com.demo.service.impl;

import com.demo.dao.BloggerMapper;
import com.demo.pojo.Blogger;
import com.demo.service.VisitorService;
import lombok.Data;

@Data
public class VisitorServiceImpl implements VisitorService {
    private BloggerMapper bloggerMapper;

    @Override
    public boolean existBlogger(String account) {
        Blogger blogger = bloggerMapper.selectByAccount(account);
        return blogger != null;
    }

    @Override
    public Blogger getBloggerByAccount(String account) {
        return bloggerMapper.selectByAccount(account);
    }
}
