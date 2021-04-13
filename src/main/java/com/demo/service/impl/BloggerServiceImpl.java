package com.demo.service.impl;

import com.demo.dao.ArticleMapper;
import com.demo.dao.BloggerMapper;
import com.demo.pojo.Article;
import com.demo.pojo.Blogger;
import com.demo.service.BloggerService;
import com.demo.util.PLog;

import java.util.List;

public class BloggerServiceImpl implements BloggerService {
    private BloggerMapper bloggerMapper;
    private ArticleMapper articleMapper;

    public void setBloggerMapper(BloggerMapper bloggerMapper) {
        this.bloggerMapper = bloggerMapper;
    }

    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public boolean checkBlogger(String account, String password) {
        Blogger blogger = bloggerMapper.selectByAccount(account);
        return blogger != null && blogger.getPassword().equals(password);
    }

    @Override
    public List<Article> getArticleByAccount(String account) {
        PLog.d(account);
        return articleMapper.selectByAccount(account);
    }

    @Override
    public Blogger getBloggerByAccount(String account) {
        return bloggerMapper.selectByAccount(account);
    }
}
