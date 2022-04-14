package com.demo.service;

import com.demo.entity.Mediae;
import java.util.List;

/**
 * (ErResource)表服务接口
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
public interface MediaeService {
    Mediae queryById(Integer id);
    List<Mediae> queryAllByLimit(int offset, int limit);
    Mediae insert(Mediae mediae);
    boolean deleteById(Integer id);

}