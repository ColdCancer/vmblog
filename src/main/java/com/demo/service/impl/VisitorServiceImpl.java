package com.demo.service.impl;

import com.demo.dao.ArticleMapper;
import com.demo.dao.BloggerMapper;
import com.demo.pojo.Article;
import com.demo.pojo.Blogger;
import com.demo.service.VisitorService;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class VisitorServiceImpl implements VisitorService {
    private BloggerMapper bloggerMapper;
    private ArticleMapper articleMapper;

    @Override
    public boolean existBlogger(String account) {
        Blogger blogger = bloggerMapper.selectByAccount(account);
        return blogger != null;
    }

    @Override
    public Blogger getBloggerByAccount(String account) {
        return bloggerMapper.selectByAccount(account);
    }

    @Override
    public List<Article> getAllArticleByAccount(String account) {
        return articleMapper.selectByAccount(account);
    }
}
