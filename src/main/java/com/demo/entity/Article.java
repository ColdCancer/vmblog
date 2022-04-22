package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (Article)实体类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article implements Serializable {
    private static final long serialVersionUID = 329121849347905454L;
    
    private Integer id;
    private Integer bloggerId;
    private Integer coverId;
    private String title;
    private String linkName;
    private String fileName;
    private String segment;
    private Date postDate;
    private Date updateDate;
    private Integer topRank;
    private Integer visCount;
}