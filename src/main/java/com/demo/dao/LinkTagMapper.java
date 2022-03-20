package com.demo.dao;

import com.demo.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LinkTagMapper {
    @Select("select count(*) from link_tag where account=#{account} and name=#{name}")
    int selectCountByKey(Tag tag);
}
