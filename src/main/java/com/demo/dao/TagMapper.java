package com.demo.dao;

import com.demo.pojo.Tag;

import java.util.List;

public interface TagMapper {
    List<Tag> selectAllTags(String account);
}
