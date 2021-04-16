package com.demo.service;

import com.demo.pojo.Article;
import com.demo.pojo.Blogger;

import java.util.Date;
import java.util.List;

public interface BloggerService {
    public boolean checkBlogger(String account, String password);
    public Blogger getBloggerByAccount(String account);
    public List<Article> getArticleByAccount(String account);
    public Article getReccentPostArticle(String account);
}
