package com.demo.service.impl;

import com.demo.entity.CategoryLink;
import com.demo.dao.CategoryLinkDao;
import com.demo.service.CategoryLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CategoryLink)表服务实现类
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@Service("categoryLinkService")
public class CategoryLinkServiceImpl implements CategoryLinkService {
    @Resource
    private CategoryLinkDao categoryLinkDao;

    @Override
    public CategoryLink queryById(Integer id) {
        return this.categoryLinkDao.queryById(id);
    }

    @Override
    public List<CategoryLink> queryAllByLimit(int offset, int limit) {
        return this.categoryLinkDao.queryAllByLimit(offset, limit);
    }

    @Override
    public CategoryLink insert(CategoryLink categoryLink) {
        this.categoryLinkDao.insert(categoryLink);
        return categoryLink;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.categoryLinkDao.deleteById(id) > 0;
    }

    @Override
    public int update(CategoryLink categoryLink) {
        return this.categoryLinkDao.update(categoryLink);
    }

    @Override
    public List<CategoryLink> queryByCategoryId(Integer categoryId, int offset, int limit) {
        return this.categoryLinkDao.queryByCategoryId(categoryId, offset, limit);
    }

    @Override
    public int queryCountByCategoryId(Integer categoryId) {
        return this.categoryLinkDao.queryCountByCategoryId(categoryId);
    }

    @Override
    public CategoryLink queryByArticleId(Integer articleId) {
        return this.categoryLinkDao.queryByArticleId(articleId);
    }

    @Override
    public Integer queryCountById(Integer categoryId) {
        return this.categoryLinkDao.queryCountById(categoryId);
    }
}