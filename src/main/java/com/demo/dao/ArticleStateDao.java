package com.demo.dao;

import com.demo.entity.ArticleState;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Insert("insert into article_state(blogger_id, article_id, on_state) " +
            "values(#{bloggerId}, #{articleId}, #{onState})")
    int insert(ArticleState articleState);

    int deleteById(Integer id);

    @Select("select count(*) from article_state where article_id=#{id} and on_state=#{state}")
    int queryCountByArticleId(@Param("id") Integer id, @Param("state") String state);

    @Select("select * from article_state where blogger_id=#{bloggerId} and article_id=#{articleId}")
    ArticleState queryByUniqe(@Param("bloggerId") Integer bloggerId, @Param("articleId") Integer articleId);

    @Update("update article_state set on_state=#{state} where blogger_id=#{bloggerId} and article_id=#{articleId}")
    int updateState(@Param("bloggerId") Integer bloggerId, @Param("articleId") Integer articleId, @Param("state") String state);

}