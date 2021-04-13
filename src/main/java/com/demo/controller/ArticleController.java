package com.demo.controller;

import com.demo.pojo.Article;
import com.demo.pojo.Blogger;
import com.demo.service.BloggerService;
import com.demo.util.PLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired()
    @Qualifier("bloggerServiceImpl")
    private BloggerService bloggerService;


    /*==================About foreword article web====================*/
    @GetMapping("/admin/article")
    public String forewordAdminArticle() {
        return "admin-article";
    }

    @SneakyThrows
    @GetMapping("/admin/article/getArticleList")
    @ResponseBody
    public String getArticleList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Blogger blogger = (Blogger) session.getAttribute("blogger");
//        PLog.d(blogger.getAccount());
        List<Article> articles = bloggerService.getArticleByAccount(blogger.getAccount());
        final ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> message = new ArrayList<HashMap<String, String>>();
        for (Article article : articles) {
            HashMap<String, String> articleJson = new HashMap<String, String>();
            articleJson.put("title", article.getTitle());
            articleJson.put("src", article.getName());
            message.add(articleJson);
        }
        return mapper.writeValueAsString(message);
    }

}
