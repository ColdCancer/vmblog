package com.demo.dao;

import com.demo.pojo.Tag;

public interface LinkTagMapper {
    int selectCountByKey(Tag tag);
}
