package com.demo.service.impl;

import com.demo.entity.Mediae;
import com.demo.dao.MediaeDao;
import com.demo.service.MediaeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ErResource)表服务实现类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Service("mediaeService")
public class MediaeServiceImpl implements MediaeService {
    @Resource
    private MediaeDao mediaeDao;

    @Override
    public Mediae queryById(Integer id) {
        return this.mediaeDao.queryById(id);
    }

    @Override
    public List<Mediae> queryAllByLimit(int offset, int limit) {
        return this.mediaeDao.queryAllByLimit(offset, limit);
    }

    @Override
    public Mediae insert(Mediae mediae) {
        this.mediaeDao.insert(mediae);
        return mediae;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.mediaeDao.deleteById(id) > 0;
    }
}