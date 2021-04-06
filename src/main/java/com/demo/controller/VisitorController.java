package com.demo.controller;

import com.demo.pojo.Article;
import com.demo.pojo.Blogger;
import com.demo.service.VisitorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class VisitorController {
    @Autowired()
    @Qualifier("visitorServiceImpl")
    private VisitorService visitorService;
    private final ObjectMapper mapper = new ObjectMapper();

    /*===============About Visitor to access a blog home===============*/
    @RequestMapping("/{account}/home")
    public String redirectBloogerHome(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-home" : "404";
    }

    /*About ajax of home.html get a total of message*/
    @SneakyThrows
    @GetMapping(value="/{account}/home/getMessageInHome", produces="application/json; charset=utf-8")
    @ResponseBody
    public String getMessageInHome(@PathVariable("account") String account) {
        HashMap<String, String> message = new HashMap<String, String>();
        Blogger blogger = visitorService.getBloggerByAccount(account);
        /*About blogger information*/
        message.put("name", blogger.getName());
        message.put("birthplace", blogger.getBirthplace());
        message.put("email", blogger.getEmail());
        message.put("sex", blogger.getSex());
        message.put("education", blogger.getEducation());
        message.put("position", blogger.getPosition());

        /*About what talk*/
        message.put("talk", blogger.getTalk());

        /*About Web message*/

        return mapper.writeValueAsString(message);
    }

    /*==============About Visitor to access a blog article=============*/
    @RequestMapping("/{account}/article")
    public String redirectBloogerArticle(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-article" : "404";
    }

    @SneakyThrows
    @GetMapping(value="/{account}/article/getMessageInArticle", produces="application/json; charset=utf-8")
    @ResponseBody
    public String getMessageInArticle(@PathVariable("account") String account) {
        List<HashMap<String, String>> articlesJson = new ArrayList<HashMap<String, String>>();
        List<Article> articles = visitorService.getAllArticleByAccount(account);
        for(Article article : articles) {
//            System.out.println(article.getName());
            HashMap<String, String> articleJson = new HashMap<String, String>();
            articleJson.put("article-id", article.getId());
            articleJson.put("article-grade", Integer.toString(article.getGrade()));
            articleJson.put("article-title", article.getTitle());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            articleJson.put("article-release", format.format(article.getPost_time()));
            articlesJson.add(articleJson);
        }
        return mapper.writeValueAsString(articlesJson);
    }


    /*===============About Visitor to access a blog link===============*/
    @RequestMapping("/{account}/link")
    public String redirectBloogerLink(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-link" : "404";
    }

    /*===============About Visitor to access a blog about==============*/
    @RequestMapping("/{account}/about")
    public String redirectBloogerAbout(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-about" : "404";
    }


}
