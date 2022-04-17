package com.demo.service.impl;

import com.demo.entity.ArticleState;
import com.demo.dao.ArticleStateDao;
import com.demo.service.ArticleStateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ArticleState)表服务实现类
 *
 * @author vmice
 * @since 2022-04-17 22:24:57
 */
@Service("articleStateService")
public class ArticleStateServiceImpl implements ArticleStateService {
    @Resource
    private ArticleStateDao articleStateDao;

    @Override
    public ArticleState queryById(Integer id) {
        return this.articleStateDao.queryById(id);
    }

    @Override
    public List<ArticleState> queryAllByLimit(int offset, int limit) {
        return this.articleStateDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ArticleState insert(ArticleState articleState) {
        this.articleStateDao.insert(articleState);
        return articleState;
    }

    @Override
    public int queryCountByArticleId(Integer id, String state) {
        return this.articleStateDao.queryCountByArticleId(id, state);
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.articleStateDao.deleteById(id) > 0;
    }
}