package com.demo.service.impl;

import com.demo.entity.ErResource;
import com.demo.dao.ErResourceDao;
import com.demo.service.ErResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ErResource)表服务实现类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Service("erResourceService")
public class ErResourceServiceImpl implements ErResourceService {
    @Resource
    private ErResourceDao erResourceDao;

    @Override
    public ErResource queryById(Integer id) {
        return this.erResourceDao.queryById(id);
    }

    @Override
    public List<ErResource> queryAllByLimit(int offset, int limit) {
        return this.erResourceDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ErResource insert(ErResource erResource) {
        this.erResourceDao.insert(erResource);
        return erResource;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.erResourceDao.deleteById(id) > 0;
    }
}