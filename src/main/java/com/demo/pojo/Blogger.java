package com.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blogger {
    private String account;
    private String password;
    private String name;
    private String sex;
    private String birthplace;
    private String education;
    private String position;
    private String talk;
    private int age;
    private String email;
    private Date register;
}
