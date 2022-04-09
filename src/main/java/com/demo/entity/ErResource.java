package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (ErResource)实体类
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErResource implements Serializable {
    private static final long serialVersionUID = 877197592084574425L;
    
    private Integer id;
    private Integer bloggerId;
    private String linkName;
    private String fileName;
    private String flagType;
    private Date postDate;
}