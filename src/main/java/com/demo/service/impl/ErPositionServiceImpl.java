package com.demo.service.impl;

import com.demo.entity.ErPosition;
import com.demo.dao.ErPositionDao;
import com.demo.service.ErPositionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ErPosition)表服务实现类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Service("erPositionService")
public class ErPositionServiceImpl implements ErPositionService {
    @Resource
    private ErPositionDao erPositionDao;

    @Override
    public ErPosition queryById(Integer id) {
        return this.erPositionDao.queryById(id);
    }

    @Override
    public List<ErPosition> queryAllByLimit(int offset, int limit) {
        return this.erPositionDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ErPosition insert(ErPosition erPosition) {
        this.erPositionDao.insert(erPosition);
        return erPosition;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.erPositionDao.deleteById(id) > 0;
    }
}