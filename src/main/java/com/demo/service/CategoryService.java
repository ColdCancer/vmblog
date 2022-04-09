package com.demo.service;

import com.demo.entity.Category;
import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
public interface CategoryService {
    Category queryById(Integer id);
    List<Category> queryAllByLimit(int offset, int limit);
    Category insert(Category category);
    boolean deleteById(Integer id);
}