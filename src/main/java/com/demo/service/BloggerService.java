package com.demo.service;

import com.demo.entity.Blogger;

import java.util.Date;
import java.util.List;

/**
 * (Blogger)表服务接口
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
public interface BloggerService {
    Blogger queryById(Integer id);
    List<Blogger> queryAllByLimit(int offset, int limit);
    Blogger insert(Blogger blogger);
    boolean deleteById(Integer id);
    Blogger queryByAccount(String account);
    boolean checkAccountValid(String account, String password, boolean remember);

    int updateLastDate(Integer id, Date date);
}