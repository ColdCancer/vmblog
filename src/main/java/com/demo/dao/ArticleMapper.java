package com.demo.dao;

import com.demo.pojo.Article;
import com.demo.service.impl.BloggerServiceImpl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("select * from article where account=#{account}")
    List<Article> selectByAccount(String account);

    @Select("select * from article order by post_time desc limit 1")
    List<Article> selectFirstPostArticle(String account);

    @Insert("insert into article(id, account, title, post_time) " +
            "values (#{id}, #{account}, #{title}, #{post_time})")
    boolean insertArticle(Article article);

    @Select("select count(*) from article where account=#{account}")
    int countByAccount(String account);

    @Select("select count(*) from article where id=#{id} and account=#{account}")
    int countByIdAndAccount(Article article);

}
