package com.demo.dao;

import com.demo.entity.Article;
import com.demo.entity.Blogger;
import org.apache.ibatis.annotations.*;

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
//            @Result(property = "likeCount", column = "like_count"),
//            @Result(property = "dislikeCount", column = "dislike_count"),
//            @Result(property = "visCount", column = "vis_count")
//    })

    @Select("select * from article where id=#{id}")
    Article queryById(Integer id);

//    @ResultMap(value = {"ArticleResultMap"})
    @Select("select * from article limit #{limit}, #{offset}")
    List<Article> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("insert into article(blogger_id, title, link_name, file_name, flag_type, post_date) " +
            "values(#{bloggerId}, #{title}, #{linkName}, #{fileName}, #{flagType}, #{postDate})")
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

    @Select("select * from article where blogger_id=#{id} and file_name=#{name}")
    Article queryByBloggerIdAndLinkName(@Param("id") Integer bloggerId, @Param("name") String fileName);
}