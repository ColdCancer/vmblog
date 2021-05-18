package com.demo.controller;

import com.demo.pojo.Article;
import com.demo.pojo.Blogger;
import com.demo.service.VisitorService;
import com.demo.util.ControllerTools;
import com.demo.util.Tools;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class VisitorController {
    @Autowired()
    @Qualifier("visitorServiceImpl")
    private VisitorService visitorService;

    private static final byte[] bytes = new byte[512];

    /*=============About Visitor to access blog web index==============*/
    @GetMapping("/")
    public String jumpWebIndex() {
        return "index";
    }

    @GetMapping("/home")
    public String jumpWebHome() {
        return "index";
    }

    @GetMapping("/coding")
    public String jumpWebCoding() {
        return "coding";
    }
    @GetMapping("/coding/loadingPattern")
    @ResponseBody
    public String loadingPattern() {
        return "Debug";
    }

    @GetMapping("/chet")
    public String jumpWebChet() {
        return "chet";
    }

    @GetMapping("/about")
    public String jumpWebAbout() {
        return "about";
    }

    /*===============About Visitor to access a blog home===============*/
    @GetMapping("/{account}/home")
    public String CheckBloogerHome(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-home" : "404";
    }
    @GetMapping("/{account}/home.html")
    public String redirectBloogerHome(@PathVariable("account") String account) {
        return "redirect:/" + account + "/home";
    }

    /*About ajax of home.html get a total of message*/
    @SneakyThrows
    @GetMapping(value="/{account}/home/getMessageInHome", produces="application/json; charset=utf-8")
    @ResponseBody
    public String getMessageInHome(@PathVariable("account") String account) {
        final ObjectMapper mapper = new ObjectMapper();
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
    @GetMapping("/{account}/article")
    public String CheckBloogerArticle(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-article" : "404";
    }
    @GetMapping("/{account}/article.html")
    public String redirectBloogerArticle(@PathVariable("account") String account) {
        return "redirect:/" + account + "/article";
    }

    @SneakyThrows
    @GetMapping(value="/{account}/article/getMessageInArticle", produces="application/json; charset=utf-8")
    @ResponseBody
    public String getMessageInArticle(@PathVariable("account") String account, HttpServletRequest request) {
        final String basePath = request.getSession().getServletContext().getRealPath("/WEB-INF/article");
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> articlesJson = new ArrayList<HashMap<String, String>>();
        List<Article> articles = visitorService.getAllArticleByAccount(account);

        Tools.log("" + articles.size());
        /*deal with all article and add JsonList*/
        for(Article article : articles) {
            HashMap<String, String> articleJson = new HashMap<String, String>();

            /*about aritcle base message*/
            articleJson.put("article-id", article.getId());
            articleJson.put("article-grade", Integer.toString(article.getGrade()));
            articleJson.put("article-title", article.getTitle());

            articleJson.put("article-release", format.format(article.getPost_time()));

            /*about article a lot of content*/
            String filePaht = String.format("\\%s\\%s.md", account, article.getId());

            try {
                File articleFile = new File(basePath + filePaht);
                new FileInputStream(articleFile).read(bytes);
                articleJson.put("article-content", new String(bytes));
            } catch (Exception e) {
                Tools.log(article.getId() + " post failure");
            }

            /*add a article all meesage*/
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
