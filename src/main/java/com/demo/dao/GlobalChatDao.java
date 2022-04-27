package com.demo.dao;

import com.demo.entity.GlobalChat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * (GlobalChat)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface GlobalChatDao {
    // @Results(id = "GlobalChatResultMap", value = {
    //         @Result(property = "id", column = "id"),
    //         @Result(property = "bloggerId", column = "blogger_id"),
    //         @Result(property = "postDate", column = "post_date"),
    //         @Result(property = "topicContent", column = "topic_content")
    // })

    @Select("select * from global_chat where id=#{id}")
    GlobalChat queryById(Integer id);

    @Select("select * from global_chat limit #{limit}, #{offset}")
    List<GlobalChat> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into global_chat(blogger_id, topic_content) " +
            "values (#{bloggerId}, #{topicContent})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(GlobalChat globalChat);

    @Delete("delete from global_chat where id=#{id}")
    int deleteById(Integer id);

    @Select("select * from global_chat order by post_date limit #{total}")
    List<GlobalChat> queryByCount(int total);
}