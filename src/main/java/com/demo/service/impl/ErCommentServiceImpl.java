package com.demo.service.impl;

import com.demo.entity.ErComment;
import com.demo.dao.ErCommentDao;
import com.demo.service.ErCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ErComment)表服务实现类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Service("erCommentService")
public class ErCommentServiceImpl implements ErCommentService {
    @Resource
    private ErCommentDao erCommentDao;

    @Override
    public ErComment queryById(Integer id) {
        return this.erCommentDao.queryById(id);
    }

    @Override
    public List<ErComment> queryAllByLimit(int offset, int limit) {
        return this.erCommentDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ErComment insert(ErComment erComment) {
        this.erCommentDao.insert(erComment);
        return erComment;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.erCommentDao.deleteById(id) > 0;
    }
}