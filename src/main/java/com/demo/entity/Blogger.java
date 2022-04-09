package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (Blogger)实体类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Blogger implements Serializable {
    private static final long serialVersionUID = -89762106162158877L;
    
    private Integer id;
    private String erName;
    private String erSex;
    private String erMotto;
    private String erAccount;
    private String erPassword;
    private String saSalt;
    private Date registerDate;
    private Date lastLoginDate;
}