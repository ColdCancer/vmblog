package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (CategoryLink)实体类
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryLink implements Serializable {
    private static final long serialVersionUID = -45234266083561344L;
    
    private Integer id;
    private Integer articleId;
    private Integer categoryId;
}