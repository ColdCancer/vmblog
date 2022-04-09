package com.demo.dao;

import com.demo.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (Category)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface CategoryDao {
    @Results(id = "CategoryResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "bloggerId", column = "blogger_id"),
            @Result(property = "typeName", column = "type_name")
    })

    @Select("select * from category where id=#{id}")
    Category queryById(Integer id);

    @Select("select * from category limit #{limit}, #{offset}")
    List<Category> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into category(blogger_id, type_name) " +
            "values(#{bloggerId}, #{typeName})")
    int insert(Category category);

    @Delete("delete from category where id=#{id}")
    int deleteById(Integer id);

}