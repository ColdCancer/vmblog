package com.demo.dao;

import com.demo.pojo.Article;

import java.util.Date;
import java.util.List;

public interface ArticleMapper {
    public List<Article> selectByAccount(String account);
    public List<Article> selectFirstPostArticle(String account);
}
