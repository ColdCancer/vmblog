package com.demo.service;

import com.demo.entity.ErDevelopment;
import java.util.List;

/**
 * (ErDevelopment)表服务接口
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
public interface ErDevelopmentService {
    ErDevelopment queryById(Integer id);
    List<ErDevelopment> queryAllByLimit(int offset, int limit);
    ErDevelopment insert(ErDevelopment erDevelopment);
    boolean deleteById(Integer id);
}