package com.demo.dao;

import com.demo.entity.ErResource;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (ErResource)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface ErResourceDao {
    @Results(id = "ErResourceResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "bloggerId", column = "blogger_id"),
            @Result(property = "like_name", column = "like_name"),
            @Result(property = "file_name", column = "file_name"),
            @Result(property = "flag_type", column = "flag_type"),
            @Result(property = "post_date", column = "post_date")
    })

    @Select("select * from er_resource where id=#{id}")
    ErResource queryById(Integer id);

    @Select("select * from er_resource limit #{limit}, #{offset}")
    List<ErResource> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into er_resource(blogger_id, link_name, file_name, flag_type) " +
            "values(#{bloggerId}, #{linkName}, #{fileName}, #{flagType})")
    int insert(ErResource erResource);

    @Delete("delete from er_resource where id=#{id}")
    int deleteById(Integer id);

}