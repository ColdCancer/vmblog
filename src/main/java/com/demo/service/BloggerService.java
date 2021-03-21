package com.demo.service;

import com.demo.pojo.Blogger;

public interface BloggerService {
    public boolean checkBlogger(String account, String password);
    public Blogger getBloggerByAccount(String account);
}
