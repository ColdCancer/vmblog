package com.demo.service;

import com.demo.entity.GlobalChat;
import java.util.List;

/**
 * (GlobalChat)表服务接口
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
public interface GlobalChatService {
    GlobalChat queryById(Integer id);
    List<GlobalChat> queryAllByLimit(int offset, int limit);
    GlobalChat insert(GlobalChat globalChat);
    boolean deleteById(Integer id);

}