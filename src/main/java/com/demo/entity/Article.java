package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

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
    private Timestamp post_time;
}
