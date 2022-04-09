package com.demo.service;

import com.demo.entity.ErComment;
import java.util.List;

/**
 * (ErComment)表服务接口
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
public interface ErCommentService {
    ErComment queryById(Integer id);
    List<ErComment> queryAllByLimit(int offset, int limit);
    ErComment insert(ErComment erComment);
    boolean deleteById(Integer id);

}