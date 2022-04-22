package com.demo.service;

import com.demo.entity.ArticleSave;
import java.util.List;

/**
 * (ArticleSave)表服务接口
 *
 * @author vmice
 * @since 2022-04-22 08:11:48
 */
public interface ArticleSaveService {

    ArticleSave queryById(Integer id);

    List<ArticleSave> queryAllByLimit(int offset, int limit);

    ArticleSave insert(ArticleSave articleSave);

    int update(ArticleSave articleSave);

    boolean deleteById(Integer id);

    ArticleSave queryByArticleId(Integer articleId);

    int deleteByArticleId(Integer articleId);
}