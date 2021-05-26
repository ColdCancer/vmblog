package com.demo.service.impl;

import com.demo.dao.ArticleMapper;
import com.demo.dao.BloggerMapper;
import com.demo.dao.LinkTagMapper;
import com.demo.dao.TagMapper;
import com.demo.pojo.Article;
import com.demo.pojo.Blogger;
import com.demo.pojo.Tag;
import com.demo.service.BloggerService;
import com.demo.util.PLog;
import com.demo.util.Tools;
import lombok.SneakyThrows;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BloggerServiceImpl implements BloggerService {
    private BloggerMapper bloggerMapper;
    private ArticleMapper articleMapper;
    private TagMapper tagMapper;
    private LinkTagMapper linkTagMapper;

    public void setBloggerMapper(BloggerMapper bloggerMapper) {
        this.bloggerMapper = bloggerMapper;
    }

    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    public void setLinkTagMapper(LinkTagMapper linkTagMapper) {
        this.linkTagMapper = linkTagMapper;
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
        String id = Tools.randomString(10);
        Article article = new Article(id, account, 5, title, Timestamp.valueOf(time));
        while (articleMapper.countByIdAndAccount(article) != 0) {
            article.setId(Tools.randomString(10));
        }
        return Tools.buildFile(account, id, content) && articleMapper.insertArticle(article);
    }

    @Override
    public List<Tag> getTagByAccount(String account) {
        return tagMapper.selectAllTags(account);
    }

    @Override
    public boolean addTagByAccount(String account, String tagName) {
        return tagMapper.insertTag(account, tagName) == 1;
    }

    @Override
    public int getTagLinkCount(Tag tag) {
        return linkTagMapper.selectCountByKey(tag);
    }

    @Override
    public Article getReccentPostArticle(String account) {
        List<Article> articleSet = articleMapper.selectFirstPostArticle(account);
        return articleSet.get(0);
    }

    @Override
    public List<Article> getArticleByAccount(String account) {
        return articleMapper.selectByAccount(account);
    }

    @Override
    public Blogger getBloggerByAccount(String account) {
        return bloggerMapper.selectByAccount(account);
    }


}
