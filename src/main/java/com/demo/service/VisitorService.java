package com.demo.service;

import com.demo.pojo.Article;
import com.demo.pojo.Blogger;

import java.util.HashMap;
import java.util.List;

public interface VisitorService {
    public boolean existBlogger(String account);
    public Blogger getBloggerByAccount(String account);
    public List<Article> getAllArticleByAccount(String account);
}
