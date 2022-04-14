package com.demo.dao;

import com.demo.entity.Mediae;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (ErResource)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface MediaeDao {
//    @Results(id = "ErResourceResultMap", value = {
//            @Result(property = "id", column = "id"),
//            @Result(property = "bloggerId", column = "blogger_id"),
//            @Result(property = "like_name", column = "like_name"),
//            @Result(property = "file_name", column = "file_name"),
//            @Result(property = "flag_type", column = "flag_type"),
//            @Result(property = "post_date", column = "post_date")
//    })

    @Select("select * from mediae where id=#{id}")
    Mediae queryById(Integer id);

    @Select("select * from mediae limit #{limit}, #{offset}")
    List<Mediae> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into mediae(blogger_id, md_name, md_digest, flag_type) " +
            "values(#{bloggerId}, #{mdName}, #{mdDigest}, #{flagType})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insert(Mediae mediae);

    @Delete("delete from mediae where id=#{id}")
    int deleteById(Integer id);

}