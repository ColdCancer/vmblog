package com.demo.dao;

import com.demo.entity.ErComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (ErComment)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-23 14:39:53
 */
public interface ErCommentDao {

    @Select("select * from er_comment where id=#{id}")
    ErComment queryById(Integer id);

    List<ErComment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into er_comment(from_blogger_id, to_blogger_id, article_id, topic_content, " +
            "post_date, parent_id) values(#{fromBloggerId}, #{toBloggerId}, #{articleId}, " +
            "#{topicContent}, #{postDate}, #{parentId})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(ErComment erComment);

    int update(ErComment erComment);

    int deleteById(Integer id);

    @Select("select * from er_comment where article_id=#{articleId}")
    List<ErComment> queryByArticleId(Integer articleId);

    @Select("select * from er_comment where from_blogger_id=#{bloggerId} " +
            "and delete_flag=0 order by id desc limit #{limit}, #{offset}")
    List<ErComment> queryAllByBloggerAndLimit(@Param("bloggerId") Integer bloggerId, @Param("offset") int offset, @Param("limit") int limit);

    @Update("update er_comment set delete_flag=1 where id=#{id}")
    int logicDelete(Integer id);

    @Select("select * from er_comment where delete_flag=0 order by post_date desc limit #{number}")
    List<ErComment> queryByCurrent(int number);

    @Select("select * from er_comment where from_blogger_id=#{bloggerId} order by post_date desc limit #{limit}")
    List<ErComment> queryByBloggerIdLimit(@Param("bloggerId") Integer bloggerId, @Param("limit") int limit);

    @Select("select count(*) from er_comment where from_blogger_id=#{bloggerId} and delete_flag=0")
    int queryCountByBloggerId(Integer bloggerId);

    @Select("select * from er_comment where from_blogger_id=#{bloggerId} and delete_flag=0 order by post_date desc limit #{number}")
    List<ErComment> queryCurrentByBloggerId(@Param("bloggerId") Integer bloggerId, @Param("number") int number);
}