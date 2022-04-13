package com.demo.controller;

import com.demo.entity.Article;
import com.demo.entity.Blogger;
import com.demo.service.ArticleService;
import com.demo.service.BloggerService;
import com.demo.utils.BaseTools;
import com.demo.utils.ResponseData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Article)表控制层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@RestController
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private BloggerService bloggerService;

    @GetMapping("selectOne")
    public Article selectOne(Integer id) {
        return this.articleService.queryById(id);
    }

    @GetMapping("/api/article/{blogger}/{link}")
    public ResponseData getArticleByLink(Integer id) {
//        return this.articleService.queryById(id);
        return new ResponseData(0, "article", null);
    }

    @PostMapping("/api/article/addCover")
    public ResponseData addCover(@RequestParam("cover") MultipartFile coverFile) {
        System.out.println(coverFile);
//        return this.articleService.queryById(id);
        return new ResponseData(0, "cover", null);
    }

    @GetMapping("/api/article/page/{number}")
    public ResponseData getArticlesByPageNumber(
                    @PathVariable("number") Integer number) {
        int number_of_one_page = 5;
        int begin_index = (number - 1) * number_of_one_page;
        List<Article> articleList = articleService.queryAllByLimit(
                number_of_one_page, begin_index);
        ResponseData responseData = null;
        if (articleList != null) {
            Map<String, Object> data = new HashMap<String, Object>();
            for (int i = 0; i < articleList.size(); i++) {
                Article article = articleList.get(i);
                Map<String, Object> mapper = new HashMap<String, Object>();
                Blogger blogger = bloggerService.queryById(article.getBloggerId());
                mapper.put("title", article.getTitle());
                String cover_path = File.separator + "api" + File.separator +
                        "mediae" + File.separator + "images" +
                        File.separator + "article-cover-default.jpg";
                // cover_path: /api/resources/images/article-cover-default.jpg
                mapper.put("segmental", "defult");
                mapper.put("cover", cover_path);
                mapper.put("post", "3 mins");
                mapper.put("blogger", blogger.getErName());
                mapper.put("views", article.getVisCount());
                mapper.put("like", article.getLikeCount());
                mapper.put("dislike", article.getDislikeCount());
                mapper.put("link", article.getLinkName());
                data.put("" + (i + 1), mapper);
            }
            responseData = new ResponseData(0, "default", data);
        } else {
            responseData = new ResponseData(-1, "failure", null);
        }
        return responseData;
    }

    @PostMapping("/api/article/add")
    public ResponseData addArticle(HttpSession session,
                       @RequestParam("title") String title,
                       @RequestParam("article") String article,
                       @RequestParam("postDate") String postDate) {
        String account = (String) session.getAttribute("account");
        String link_name = BaseTools.RandomStr(12);
        String file_name = BaseTools.RandomStr(20);
        String base_path = session.getServletContext().getRealPath("/WEB-INF/") +
                "blogger" + File.separator + account + File.separator + "article";
        String file_path = base_path + File.separator + file_name;
        postDate = postDate.replace("T", " ");

        File directory = new File(base_path);
        if (!directory.exists()) {
            directory.mkdir();
        }

        ResponseData responseData = null;
        try {
            FileWriter writer = new FileWriter(file_path);
            writer.write(article);
            writer.flush();
            writer.close();
            Blogger blogger = bloggerService.queryByAccount(account);
            Article article_ojb = new Article(0, blogger.getId(),
                    null, title, link_name, file_name, "posted",
                    BaseTools.toDate(postDate), null, 0,
                    0, 0, 0);
//            System.out.println(article_ojb);
//            articleService.insert(article_ojb);
            article_ojb = articleService.insertAndReturn(article_ojb);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("account", account);
            data.put("article-link", article_ojb.getLinkName());
            responseData = new ResponseData(0, "success", data);
        } catch (IOException e) {
            responseData = new ResponseData(-1, "failure", null);
        }
        return responseData;
    }

}