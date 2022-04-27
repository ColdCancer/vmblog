package com.demo.service.impl;

import com.demo.entity.GlobalChat;
import com.demo.dao.GlobalChatDao;
import com.demo.service.GlobalChatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (GlobalChat)表服务实现类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Service("globalChatService")
public class GlobalChatServiceImpl implements GlobalChatService {
    @Resource
    private GlobalChatDao globalChatDao;

    @Override
    public GlobalChat queryById(Integer id) {
        return this.globalChatDao.queryById(id);
    }

    @Override
    public List<GlobalChat> queryAllByLimit(int offset, int limit) {
        return this.globalChatDao.queryAllByLimit(offset, limit);
    }

    @Override
    public GlobalChat insert(GlobalChat globalChat) {
        this.globalChatDao.insert(globalChat);
        return globalChat;
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.globalChatDao.deleteById(id) > 0;
    }

    @Override
    public List<GlobalChat> queryByCount(int total) {
        return this.globalChatDao.queryByCount(total);
    }
}