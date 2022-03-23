package com.demo.service;

import com.demo.entity.Article;
import com.demo.entity.Blogger;

import java.util.List;

public interface VisitorService {
    public boolean existBlogger(String account);
    public Blogger getBloggerByAccount(String account);
    public List<Article> getAllArticleByAccount(String account);
}
