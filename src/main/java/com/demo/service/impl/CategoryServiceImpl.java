package com.demo.service.impl;

import com.demo.entity.Category;
import com.demo.dao.CategoryDao;
import com.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Category)表服务实现类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryDao categoryDao;

    @Override
    public Category queryById(Integer id) {
        return this.categoryDao.queryById(id);
    }

    @Override
    public List<Category> queryAllByLimit(int offset, int limit) {
        return this.categoryDao.queryAllByLimit(offset, limit);
    }

    @Override
    public Category insert(Category category) {
        this.categoryDao.insert(category);
        return category;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.categoryDao.deleteById(id) > 0;
    }
}