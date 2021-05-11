package com.demo.service.impl;

import com.demo.dao.ArticleMapper;
import com.demo.dao.BloggerMapper;
import com.demo.pojo.Article;
import com.demo.pojo.Blogger;
import com.demo.service.BloggerService;
import com.demo.util.PLog;
import com.demo.util.Tools;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @SneakyThrows
    @Override
    public boolean postArticle(String account, String title, String time, String content) {
        time = time.replace('T', ' ');
//        String id = Tools.consequncedIndex(articleMapper.countByAccount(account));
        String id = Tools.randomString(20);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Article article = new Article(id, account, 5, title, simpleDateFormat.parse(time));
        int count = articleMapper.countByIdAndAccount(article);
        return count == 0 && articleMapper.insertArticle(article);
    }

    @Override
    public Article getReccentPostArticle(String account) {
        List<Article> articleSet = articleMapper.selectFirstPostArticle(account);
        return articleSet.get(0);
    }

    @Override
    public List<Article> getArticleByAccount(String account) {
//        PLog.d(account);
        return articleMapper.selectByAccount(account);
    }

    @Override
    public Blogger getBloggerByAccount(String account) {
        return bloggerMapper.selectByAccount(account);
    }
}
