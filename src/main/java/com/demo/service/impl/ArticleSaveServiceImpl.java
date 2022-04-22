package com.demo.service.impl;

import com.demo.entity.ArticleSave;
import com.demo.dao.ArticleSaveDao;
import com.demo.service.ArticleSaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ArticleSave)表服务实现类
 *
 * @author vmice
 * @since 2022-04-22 08:11:48
 */
@Service("articleSaveService")
public class ArticleSaveServiceImpl implements ArticleSaveService {
    @Resource
    private ArticleSaveDao articleSaveDao;


    @Override
    public ArticleSave queryById(Integer id) {
        return this.articleSaveDao.queryById(id);
    }


    @Override
    public List<ArticleSave> queryAllByLimit(int offset, int limit) {
        return this.articleSaveDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ArticleSave insert(ArticleSave articleSave) {
        this.articleSaveDao.insert(articleSave);
        return articleSave;
    }

    @Override
    public int deleteByArticleId(Integer articleId) {
        return this.articleSaveDao.deleteByArticleId(articleId);
    }

    @Override
    public int update(ArticleSave articleSave) {
        return this.articleSaveDao.update(articleSave);
    }

    @Override
    public ArticleSave queryByArticleId(Integer articleId) {
        return this.articleSaveDao.queryByArticleId(articleId);
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.articleSaveDao.deleteById(id) > 0;
    }
}