package com.demo.dao;

import com.demo.entity.ArticleSave;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (ArticleSave)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-22 08:11:48
 */
public interface ArticleSaveDao {

    @Select("select * from article_save where id=#{id}")
    ArticleSave queryById(@Param("id") Integer id);

    List<ArticleSave> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into article_save(blogger_id, article_id, cover_id, title," +
            "update_date, top_rank, category_id) values(#{bloggerId}, #{articleId}, " +
            "#{coverId}, #{title}, #{updateDate}, #{topRank}, #{categoryId})")
    @Options(useGeneratedKeys=true, keyColumn="id", keyProperty="id")
    int insert(ArticleSave articleSave);

    @Update("update article_save set cover_id=#{coverId}, title=#{title}, " +
            "update_date=#{updateDate}, top_rank=#{topRank}, " +
            "category_id=#{categoryId} where id=#{id}")
    int update(ArticleSave articleSave);

    int deleteById(Integer id);

    @Select("select * from article_save where article_id=#{articleId}")
    ArticleSave queryByArticleId(@Param("articleId") Integer articleId);

    @Delete("delete from article_save where article_id=#{articleId}")
    int deleteByArticleId(@Param("articleId") Integer articleId);
}