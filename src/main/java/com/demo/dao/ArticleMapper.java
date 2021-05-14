package com.demo.dao;

import com.demo.pojo.Article;
import com.demo.service.impl.BloggerServiceImpl;

import java.util.Date;
import java.util.List;

public interface ArticleMapper {
    List<Article> selectByAccount(String account);
    List<Article> selectFirstPostArticle(String account);
    boolean insertArticle(Article article);
    int countByAccount(String account);
    int countByIdAndAccount(Article article);

}
