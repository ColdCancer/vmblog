package com.demo.service.impl;

import com.demo.entity.ErContact;
import com.demo.dao.ErContactDao;
import com.demo.service.ErContactService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ErContact)表服务实现类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Service("erContactService")
public class ErContactServiceImpl implements ErContactService {
    @Resource
    private ErContactDao erContactDao;

    @Override
    public ErContact queryById(Integer id) {
        return this.erContactDao.queryById(id);
    }

    @Override
    public List<ErContact> queryAllByLimit(int offset, int limit) {
        return this.erContactDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ErContact insert(ErContact erContact) {
        this.erContactDao.insert(erContact);
        return erContact;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.erContactDao.deleteById(id) > 0;
    }
}