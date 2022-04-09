package com.demo.controller;

import com.demo.utils.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/home")
    public String home() {
        return "index";
    }

}
