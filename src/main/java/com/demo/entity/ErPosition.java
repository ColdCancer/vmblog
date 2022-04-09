package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (ErPosition)实体类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErPosition implements Serializable {
    private static final long serialVersionUID = 326484121004266120L;
    
    private Integer id;
    private Integer bloggerId;
    private String flagType;
    private String erNation;
    private String erProvince;
}