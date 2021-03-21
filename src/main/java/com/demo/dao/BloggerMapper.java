package com.demo.dao;

import com.demo.pojo.Blogger;

public interface BloggerMapper {
    public Blogger selectByAccount(String account);
}
