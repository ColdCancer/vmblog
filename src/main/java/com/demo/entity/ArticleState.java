package com.demo.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (ArticleState)实体类
 *
 * @author vmice
 * @since 2022-04-17 22:24:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleState implements Serializable {
    private static final long serialVersionUID = -73513193348491885L;
    
    private Integer id;
    private Integer bloggerId;
    private Integer articleId;
    private String onState;

}