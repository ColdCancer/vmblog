package com.demo.service.impl;

import com.demo.dao.BloggerMapper;
import com.demo.pojo.Blogger;
import com.demo.service.BloggerService;

public class BloggerServiceImpl implements BloggerService {
    private BloggerMapper bloggerMapper;

    public void setBloggerMapper(BloggerMapper bloggerMapper) {
        this.bloggerMapper = bloggerMapper;
    }

    @Override
    public boolean checkBlogger(String account, String password) {
        Blogger blogger = bloggerMapper.selectByAccount(account);
        return blogger != null && blogger.getPassword().equals(password);
    }

    @Override
    public Blogger getBloggerByAccount(String account) {
        return bloggerMapper.selectByAccount(account);
    }
}
