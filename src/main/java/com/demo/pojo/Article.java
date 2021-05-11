package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {
    private String id;
    private String account;
    private int grade;
    private String title;
//    private String name;
    private Date post_time;
}
