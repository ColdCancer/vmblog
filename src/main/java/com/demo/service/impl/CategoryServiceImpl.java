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
    public int deleteById(Integer id) {
        return this.categoryDao.deleteById(id);
    }

    @Override
    public List<Category> queryByIds(Integer bloggerId, Integer parentId) {
        return this.categoryDao.queryByIds(bloggerId, parentId);
    }

    @Override
    public int updateParentByIds(Integer id, Integer parentId) {
        return this.categoryDao.updateParentByIds(id, parentId);
    }

    @Override
    public int querySonCountByIds(Integer bloggerId, Integer parentId) {
        return this.categoryDao.querySonCountByIds(bloggerId, parentId);
    }

    @Override
    public Category queryByBloggerIdAndTypeName(Integer bloggerId, String typeName) {
        return this.categoryDao.queryByBloggerIdAndTypeName(bloggerId, typeName);
    }

    @Override
    public int updateTypeNameById(Integer id, String typeName) {
        return this.categoryDao.updateTypeNameById(id, typeName);
    }

    @Override
    public Category queryByIdAndType(Integer bloggerId, String classify) {
        return this.categoryDao.queryByIdAndType(bloggerId, classify);
    }

    @Override
    public List<Category> queryByBloggerId(Integer bloggerId) {
        return this.categoryDao.queryByBloggerId(bloggerId);
    }
}