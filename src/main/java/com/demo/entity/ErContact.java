package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (ErContact)实体类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErContact implements Serializable {
    private static final long serialVersionUID = -93372132513246327L;
    
    private Integer id;
    private Integer bloggerId;
    private String flagType;
    private String topicContent;
}