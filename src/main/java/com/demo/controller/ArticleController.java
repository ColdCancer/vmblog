package com.demo.controller;

import com.demo.pojo.Article;
import com.demo.pojo.Blogger;
import com.demo.service.BloggerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired()
    private BloggerService bloggerService;

    /*==================About foreword article web====================*/
    @GetMapping("/admin/article")
    public String forewordAdminArticle() {
        return "admin/admin-article";
    }

    /*==================About article page message====================*/
    @SneakyThrows
    @GetMapping("/admin/article/getArticleList")
    @ResponseBody
    public String getArticleList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        HashMap<String, Object> message = new HashMap<String, Object>();

        // add admin self information
        message.put("account", blogger.getAccount());

        // add article other information
        List<Article> articles = bloggerService.getArticleByAccount(blogger.getAccount());
        message.put("total", articles.size());
        Article reccent = bloggerService.getReccentPostArticle(blogger.getAccount());
        message.put("reccent", reccent.getPost_time().toString());

        // add article content information
        List<HashMap<String, String>> articleList = new ArrayList<HashMap<String, String>>();
        for (Article article : articles) {
            HashMap<String, String> articleJson = new HashMap<String, String>();
            articleJson.put("title", article.getTitle());
            articleJson.put("time", article.getPost_time().toString());
            articleJson.put("src", article.getId());
            articleList.add(articleJson);
        }
        message.put("article", articleList);

        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(message);
    }

    /*==================About add new article page====================*/
    @GetMapping("/admin/article/new")
    public String ForewordNewPage() {
        return "admin/admin-article-new";
    }

    /*=====================About add new article======================*/
    @SneakyThrows
    @PostMapping("/admin/article/new/postArticle")
    @ResponseBody
    public String postArticle(HttpServletRequest request, HttpSession session, String title, String time, String content) {
        HashMap<String, Boolean> jsonData = new HashMap<String, Boolean>();
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        boolean flag = bloggerService.postArticle(blogger.getAccount(), title, time, content);
//        System.out.println(title + "\n" + time + "\n" + content);
        jsonData.put("result", flag);
//        String path = getClass().getResource("/").getPath();
//        Tools.log(path);
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(jsonData);
    }
}
