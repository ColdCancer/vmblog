package com.demo.controller;

import com.demo.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/{name}")
public class VisitorController {
    @Autowired()
    @Qualifier("visitorServiceImpl")
    private VisitorService visitorService;
    private String name;
    private boolean visitorFlag;

    @RequestMapping("/{name}/home")
    public String redirectBloogerHome(@PathVariable("name") String name) {
        return "blogger-home";
    }

    @RequestMapping("/{name}/article")
    public String redirectBloogerArticle(@PathVariable("name") String name) {
        return "blogger-article";
    }

    @RequestMapping("/{name}/link")
    public String redirectBloogerLink(@PathVariable("name") String name) {
        return "blogger-link";
    }

    @RequestMapping("/{name}/about")
    public String redirectBloogerAbout(@PathVariable("name") String name) {
        return "blogger-about";
    }

}
