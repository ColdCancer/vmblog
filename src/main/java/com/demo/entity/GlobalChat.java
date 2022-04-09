package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.io.Serializable;

/**
 * (GlobalChat)实体类
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GlobalChat implements Serializable {
    private static final long serialVersionUID = -54253089867076350L;
    
    private Integer id;
    private Integer bloggerId;
    private Date postDate;
    private String topicContent;

}