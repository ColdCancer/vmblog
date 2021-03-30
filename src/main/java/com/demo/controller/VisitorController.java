package com.demo.controller;

import com.demo.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VisitorController {
    @Autowired()
    @Qualifier("visitorServiceImpl")
    private VisitorService visitorService;
    private String name;
    private boolean visitorFlag;

    @RequestMapping("/{account}/home")
    public String redirectBloogerHome(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-home" : "404";
    }

    @RequestMapping("/{account}/article")
    public String redirectBloogerArticle(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-article" : "404";
    }

    @RequestMapping("/{account}/link")
    public String redirectBloogerLink(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-link" : "404";
    }

    @RequestMapping("/{account}/about")
    public String redirectBloogerAbout(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-about" : "404";
    }


}
