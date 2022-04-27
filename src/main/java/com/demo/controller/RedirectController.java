package com.demo.controller;

import com.demo.utils.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vmice
 * @since 2022-04-09 15:46:53
 */
@Controller
public class RedirectController {

    @GetMapping("/web/dashboard")
    public String toDashboard() {
        return "redirect:dashboard/information";
    }

    @GetMapping("/web/dashboard/information")
    public String toInformation() {
        return "dashboard/information";
    }

    @GetMapping("/web/dashboard/article")
    public String toArticle() {
        return "dashboard/article";
    }

    @GetMapping("/web/dashboard/category")
    public String toCategory() {
        return "dashboard/category";
    }

    @GetMapping("/web/dashboard/comment")
    public String toComment() {
        return "dashboard/comment";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/article")
    public String article() {
        return "article";
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/web/editor/article/add")
    public String addArticle() {
        return "editor/article-editor";
    }

    @GetMapping("web/editor/article/edit/{link}")
    public String editArticle(@PathVariable("link") String link) {
        return "editor/article-editor";
    }

    @GetMapping("/article/{blogger}/{link}")
    public String toArticleByLink(@PathVariable("blogger") String blogger,
                                  @PathVariable("link") String like) {
        return "previous";
    }

    @GetMapping("/article/so/{kind}/{tag}")
    public String toArticleSearch(
                    @PathVariable("kind") String kind,
                    @PathVariable("tag") String tag) {
        return "search";
    }

    @GetMapping("/article/so/category/{blogger}/{typename}")
    public String toArticleSearchByCategory(
                    @PathVariable("blogger") String blogger,
                    @PathVariable("typename") String typename) {
        return "search";
    }

    @GetMapping("/category")
    public String toIndexCategory() {
        return "category";
    }

    @GetMapping("/chat")
    public String toChatIndex() {
        return "chat";
    }
}
