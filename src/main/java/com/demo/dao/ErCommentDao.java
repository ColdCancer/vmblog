package com.demo.dao;

import com.demo.entity.ErComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (ErComment)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
public interface ErCommentDao {
    @Results(id = "ErCommentResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "fromBloggerId", column = "from_blogger_id"),
            @Result(property = "toBloggerId", column = "to_blogger_id"),
            @Result(property = "articleId", column = "article_id"),
            @Result(property = "topicContent", column = "topic_content"),
            @Result(property = "postDate", column = "post_date"),
            @Result(property = "likeCount", column = "like_count"),
            @Result(property = "dislikeCount", column = "dislike_count")
    })

    @Select("select * from er_comment where id=#{id}")
    ErComment queryById(Integer id);

    @Select("select * from er_comment limit #{limit}, #{offset}")
    List<ErComment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into er_comment(from_blogger_id, to_blogger_id, article_id, topic_content) " +
            "values(#{fromBloggerId}, #{toBloggerId}, #{articleId}, #{topicContent})")
    int insert(ErComment erComment);

    @Delete("delete from er_comment where id=#{id}")
    int deleteById(Integer id);

}