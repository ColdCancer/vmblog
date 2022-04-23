package com.demo.dao;

import com.demo.entity.ErComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}