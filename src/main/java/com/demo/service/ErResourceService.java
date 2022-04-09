package com.demo.service;

import com.demo.entity.ErResource;
import java.util.List;

/**
 * (ErResource)表服务接口
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
public interface ErResourceService {
    ErResource queryById(Integer id);
    List<ErResource> queryAllByLimit(int offset, int limit);
    ErResource insert(ErResource erResource);
    boolean deleteById(Integer id);

}