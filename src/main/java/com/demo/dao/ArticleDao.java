package com.demo.dao;

import com.demo.entity.Article;
import com.demo.entity.Blogger;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * (Article)表数据库访问层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@Mapper
public interface ArticleDao {
//    @Results(id = "ArticleResultMap", value = {
//            @Result(property = "id", column = "id"),
//            @Result(property = "bloggerId", column = "blogger_id"),
//            @Result(property = "title", column = "title"),
//            @Result(property = "linkName", column = "link_name"),
//            @Result(property = "fileName", column = "file_name"),
//            @Result(property = "imageName", column = "image_name"),
//            @Result(property = "flagType", column = "flag_type"),
//            @Result(property = "postDate", column = "post_date"),
//            @Result(property = "updateDate", column = "update_date"),
//            @Result(property = "topRank", column = "top_rank"),
//            @Result(property = "visCount", column = "vis_count")
//    })

    @Select("select * from article where id=#{id}")
    Article queryById(Integer id);

//    @ResultMap(value = {"ArticleResultMap"})
    @Select("select * from article where isnull(post_date)=0 order by id desc limit #{limit}, #{offset}")
    List<Article> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into article(blogger_id, title, link_name, file_name, segment, post_date, top_rank) " +
            "values(#{bloggerId}, #{title}, #{linkName}, #{fileName}, #{segment}, #{postDate}, #{topRank})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insert(Article article);

    @Update("update article set link_name=#{linkName} where id=#{id}")
    int setLinkNameById(Integer id, String linkName);

    @Update("update article set file_name=#{fileName} where id=#{id}")
    int setFileNameById(Integer id, String fileName);

    @Update("update article set hot_rank=#{hotRank} where id=#{id}")
    int setHotRankById(Integer id, Integer hotRank);

    @Update("update article set vis_count=vis_count+1 where id=#{id}")
    int autoAddVisCountById(Integer id);

    @Delete("delete from article where id=#{id}")
    int deleteById(Integer id);

    @Select("select * from article where blogger_id=#{id} and link_name=#{name}")
    Article queryByBloggerIdAndLinkName(@Param("id") Integer account, @Param("name") String linkName);

    @Update("update article set cover_id=#{coverId}, title=#{title}, link_name=#{linkName}, " +
            "segment=#{segment}, update_date=#{updateDate}, top_rank=#{topRank} where id=#{id}")
    int update(Article article);

    @Delete("delete from article where blogger_id=#{id} and link_name=#{link}")
    int deleteByBloggerIdAndLink(@Param("id") Integer id, @Param("link") String link);

    @Select("select * from article where blogger_id=#{bloggerId} order by id desc limit #{limit}, #{offset}")
    List<Article> queryAllByBloggerIdAndLimit(@Param("bloggerId") Integer bloggerId, @Param("offset") int offset, @Param("limit") int limit);

    @Update("update article set post_date=#{postDate} where id=#{id}")
    int updatePostDateById(@Param("id") Integer id, @Param("postDate") Date postDate);

    @Update("update article set post_date=#{date}, update_date=#{date} where id=#{id}")
    int updateDatesById(@Param("id") Integer id, @Param("date") Date date);

    @Select("select * from article where isnull(post_date)=0 order by vis_count desc limit #{number}")
    List<Article> queryByCount(int number);

    @Select("select count(*) from article where blogger_id=#{bloggerId} and isnull(post_date)=0")
    int queryCountByBloggerId(Integer bloggerId);

    @Select("select * from article where blogger_id=#{bloggerId} and isnull(post_date)=0 " +
            "order by post_date desc limit 1")
    Article queryCurrentPost(Integer bloggerId);
}