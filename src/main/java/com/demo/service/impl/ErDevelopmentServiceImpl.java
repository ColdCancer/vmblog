package com.demo.service.impl;

import com.demo.entity.ErDevelopment;
import com.demo.dao.ErDevelopmentDao;
import com.demo.service.ErDevelopmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ErDevelopment)表服务实现类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Service("erDevelopmentService")
public class ErDevelopmentServiceImpl implements ErDevelopmentService {
    @Resource
    private ErDevelopmentDao erDevelopmentDao;

   @Override
    public ErDevelopment queryById(Integer id) {
        return this.erDevelopmentDao.queryById(id);
    }

    @Override
    public List<ErDevelopment> queryAllByLimit(int offset, int limit) {
        return this.erDevelopmentDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ErDevelopment insert(ErDevelopment erDevelopment) {
        this.erDevelopmentDao.insert(erDevelopment);
        return erDevelopment;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.erDevelopmentDao.deleteById(id) > 0;
    }
}