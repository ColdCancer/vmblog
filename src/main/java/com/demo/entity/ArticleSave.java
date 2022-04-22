package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (ArticleSave)实体类
 *
 * @author vmice
 * @since 2022-04-22 08:11:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleSave implements Serializable {
    private static final long serialVersionUID = 426027371490691318L;
    
    private Integer id;
    private Integer bloggerId;
    private Integer articleId;
    private Integer coverId;
    private String title;
    private Date updateDate;
    private Integer topRank;
    private Integer categoryId;

}