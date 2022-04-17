package com.demo.service;

import com.demo.entity.ArticleState;
import java.util.List;

/**
 * (ArticleState)表服务接口
 *
 * @author vmice
 * @since 2022-04-17 22:24:57
 */
public interface ArticleStateService {
    ArticleState queryById(Integer id);
    List<ArticleState> queryAllByLimit(int offset, int limit);
    ArticleState insert(ArticleState articleState);
    boolean deleteById(Integer id);

    int queryCountByArticleId(Integer id, String state);
}