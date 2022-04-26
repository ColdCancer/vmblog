package com.demo.service;

import com.demo.entity.ErComment;
import java.util.List;

/**
 * (ErComment)表服务接口
 *
 * @author vmice
 * @since 2022-04-23 14:39:55
 */
public interface ErCommentService {
    ErComment queryById(Integer id);
    List<ErComment> queryAllByLimit(int offset, int limit);
    ErComment insert(ErComment erComment);
    ErComment update(ErComment erComment);
    boolean deleteById(Integer id);
    List<ErComment> queryByArticleId(Integer articleId);

    List<ErComment> queryAllByBloggerAndLimit(Integer bloggerId, int offset, int limit);

    int logicDelete(Integer id);

    List<ErComment> queryByCurrent(int number);
}