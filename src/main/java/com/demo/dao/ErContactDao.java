package com.demo.dao;

import com.demo.entity.ErContact;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (ErContact)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface ErContactDao {
    @Results(id = "ErContactResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "bloggerId", column = "blogger_id"),
            @Result(property = "flagType", column = "flag_type"),
            @Result(property = "topicContent", column = "topic_content")
    })

    @Select("select * from er_contact where id=#{id}")
    ErContact queryById(Integer id);

    @Select("select * from er_contact limit #{limit}, #{offset}")
    List<ErContact> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into er_contact(blogger_id, flag_type, topic_content) " +
            "values(#{bloggerId}, #{flagType}, #{topicContent})")
    int insert(ErContact erContact);

    @Delete("delete * from er_contact where id=#{id}")
    int deleteById(Integer id);

}