package com.demo.dao;

import com.demo.entity.ErDevelopment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (ErDevelopment)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface ErDevelopmentDao {
    @Results(id = "ErDevelopmentResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "bloggerId", column = "blogger_id"),
            @Result(property = "devName", column = "dev_name"),
            @Result(property = "erRole", column = "er_role"),
            @Result(property = "beginDate", column = "begin_date"),
            @Result(property = "endDate", column = "end_date")
    })

    @Select("select * from er_development where id=#{id}")
    ErDevelopment queryById(Integer id);

    @Select("select * from er_development limit #{limit}, #{offset}")
    List<ErDevelopment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into er_development(blogger_id, dev_name, er_role, begin_date) " +
            "values(#{bloggerId}, #{devName}, #{erRole}, #{beginDate})")
    int insert(ErDevelopment erDevelopment);

    @Delete("delete from er_development where id=#{id}")
    int deleteById(Integer id);
}