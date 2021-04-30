package com.demo.dao;

import com.demo.pojo.Article;
import com.demo.pojo.Blogger;

public interface BloggerMapper {
    Blogger selectByAccount(String account);
//    boolean insertArticle(Article article);
}
