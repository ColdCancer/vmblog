package com.demo.service.impl;

import com.demo.entity.Article;
import com.demo.dao.ArticleDao;
import com.demo.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Article)表服务实现类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleDao articleDao;

    @Override
    public Article queryById(Integer id) {
        return this.articleDao.queryById(id);
    }

    @Override
    public List<Article> queryAllByLimit(int offset, int limit) {
        return this.articleDao.queryAllByLimit(offset, limit);
    }

    @Override
    public Article queryByAccoutAndLink(Integer account, String link) {
        return this.articleDao.queryByBloggerIdAndLinkName(account, link);
    }

    @Override
    public int addVisCount(Integer id) {
        return this.articleDao.autoAddVisCountById(id);
    }

    @Override
    public int deleteByAccountAndLink(Integer id, String link) {
        return this.articleDao.deleteByBloggerIdAndLink(id, link);
    }

    @Override
    public int update(Article article) {
        return this.articleDao.update(article);
    }

    @Override
    public Article insertAndReturn(Article article) {
        this.articleDao.insert(article);
        return this.articleDao.queryByBloggerIdAndLinkName(
                article.getBloggerId(), article.getFileName());
    }

    @Override
    public Article insert(Article article) {
        this.articleDao.insert(article);
        return article;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.articleDao.deleteById(id) > 0;
    }
}