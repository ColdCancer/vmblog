package com.demo.service;

import com.demo.entity.ErContact;
import java.util.List;

/**
 * (ErContact)表服务接口
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
public interface ErContactService {
    ErContact queryById(Integer id);
    List<ErContact> queryAllByLimit(int offset, int limit);
    ErContact insert(ErContact erContact);
    boolean deleteById(Integer id);

}