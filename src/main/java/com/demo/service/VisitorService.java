package com.demo.service;

import com.demo.pojo.Blogger;

public interface VisitorService {
    public boolean existBlogger(String account);
    public Blogger getBloggerByAccount(String account);
}
