package com.demo.dao;

import com.demo.entity.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from tag where account=#{account}")
    List<Tag> selectAllTags(String account);

    @Insert("insert into tag values(#{account}, #{name})")
    int insertTag(Tag tag);
}
