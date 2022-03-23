package com.demo.dao;

import com.demo.entity.Blogger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BloggerMapper {
    @Select("select * from blogger where account=#{account}")
    Blogger selectByAccount(String account);
//    boolean insertArticle(Article article);
}
