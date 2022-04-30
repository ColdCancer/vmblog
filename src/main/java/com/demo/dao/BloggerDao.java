package com.demo.dao;

import com.demo.entity.Blogger;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * (Blogger)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface BloggerDao {
    // @Results(id = "BloggerResultMap", value = {
    //         @Result(property = "id", column = "id"),
    //         @Result(property = "erName", column = "er_name", jdbcType = JdbcType.VARCHAR),
    //         @Result(property = "erSex", column = "er_sex", jdbcType = JdbcType.VARCHAR),
    //         @Result(property = "erMotto", column = "er_motto", jdbcType = JdbcType.VARCHAR),
    //         @Result(property = "erAccount", column = "er_account", jdbcType = JdbcType.VARCHAR),
    //         @Result(property = "erPassword", column = "er_password", jdbcType = JdbcType.VARCHAR),
    //         @Result(property = "saSalt", column = "sa_salt", jdbcType = JdbcType.VARCHAR),
    //         @Result(property = "registerDate", column = "register_date"),
    //         @Result(property = "lastLoginDate", column = "last_login_date")
    // })

    @Select("select * from blogger where id=#{id}")
    Blogger queryById(Integer id);

    @Select("select * from blogger limit #{limit}, #{offset}")
    List<Blogger> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into blogger(er_name, er_account, er_password, sa_salt) " +
            "values(#{erName}, #{erAccount}, #{erPassword}, #{saSalt})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insert(Blogger blogger);

    @Delete("delete from blogger where id=#{id}")
    int deleteById(Integer id);

    @Select("select * from blogger where er_account=#{account}")
    Blogger queryByAccount(String account);

    @Update("update blogger set last_login_date=#{date} where id=#{id}")
    int updateLastDate(@Param("id") Integer id, @Param("date") Date date);

    @Update("update blogger set er_name=#{erName}, er_sex=#{erSex}, er_motto=#{erMotto}, " +
            "er_birthplace=#{erBirthplace}, er_email=#{erEmail}, er_education=#{erEducation}, " +
            "er_company=#{erCompany}, er_password=#{erPassword} where id=#{id}")
    int update(Blogger blogger);
}