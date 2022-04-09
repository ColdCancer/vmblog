package com.demo.dao;

import com.demo.entity.CategoryLink;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (CategoryLink)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface CategoryLinkDao {
    @Results(id = "CategoryLinkResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "articleId", column = "article_id"),
            @Result(property = "categoryId", column = "category_id")
    })

    @Select("select * from category where id=#{id}")
    CategoryLink queryById(Integer id);

    @Select("select * from category limit #{limit}, #{offset}")
    List<CategoryLink> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into category_link(article_id, category_id) " +
            "values(#{articleId}, #{categoryId}")
    int insert(CategoryLink categoryLink);

    @Delete("delete from category where id=#{id}")
    int deleteById(Integer id);

}