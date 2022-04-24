package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (ErComment)实体类
 *
 * @author vmice
 * @since 2022-04-23 14:39:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErComment implements Serializable {
    private static final long serialVersionUID = 183353563674798493L;
    
    private Integer id;
    private Integer fromBloggerId;
    private Integer toBloggerId;
    private Integer articleId;
    private String topicContent;
    private Date postDate;
    private Integer deleteFlag;
    private Integer parentId;

}