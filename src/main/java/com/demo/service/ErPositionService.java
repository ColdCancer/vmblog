package com.demo.service;

import com.demo.entity.ErPosition;
import java.util.List;

/**
 * (ErPosition)表服务接口
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
public interface ErPositionService {
    ErPosition queryById(Integer id);
    List<ErPosition> queryAllByLimit(int offset, int limit);
    ErPosition insert(ErPosition erPosition);
    boolean deleteById(Integer id);
}