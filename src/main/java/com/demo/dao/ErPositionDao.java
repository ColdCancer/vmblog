package com.demo.dao;

import com.demo.entity.ErPosition;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (ErPosition)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface ErPositionDao {
    @Results(id = "ErPositionResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "bloggerId", column = "blogger_id"),
            @Result(property = "flagType", column = "flag_type"),
            @Result(property = "erNation", column = "er_nation"),
            @Result(property = "erProvince", column = "er_province")
    })

    @Select("select * from er_position where id=#{id}")
    ErPosition queryById(Integer id);

    @Select("select * from er_position limit #{limit}, #{offset}")
    List<ErPosition> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into er_position(blogger_id, flag_type, er_nation) " +
            "values(#{bloggerId}, #{flagType}, #{erNation})")
    int insert(ErPosition erPosition);

    @Delete("delete from er_position where id=#{id}")
    int deleteById(Integer id);

}