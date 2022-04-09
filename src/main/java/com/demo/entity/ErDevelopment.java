package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (ErDevelopment)实体类
 *
 * @author makejava
 * @since 2022-04-04 18:59:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErDevelopment implements Serializable {
    private static final long serialVersionUID = -81496470972669536L;
    
    private Integer id;
    private Integer bloggerId;
    private Integer positionId;
    private String devName;
    private String erRole;
    private Date beginDate;
    private Date endDate;
}