package com.demo.dao;

import com.demo.entity.ArticleState;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (ArticleState)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-17 22:24:57
 */
public interface ArticleStateDao {

    ArticleState queryById(Integer id);


    List<ArticleState> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    int insert(ArticleState articleState);

    int deleteById(Integer id);

    @Select("select count(*) from article_state where article_id=#{id} and on_state=#{state}")
    int queryCountByArticleId(@Param("id") Integer id, @Param("state") String state);
}