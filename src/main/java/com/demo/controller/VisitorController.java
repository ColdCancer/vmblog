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
@RequestMapping("/{blogger}")
public class VisitorController {
    @Autowired()
    @Qualifier("visitorServiceImpl")
    private VisitorService visitorService;
    private String name;
    private boolean visitorFlag;

    @ModelAttribute()
    public void getBloggerNameByVisitor(@PathVariable("blogger") String blogger) {
        this.visitorFlag = visitorService.existBlogger(blogger);
        if (this.visitorFlag) {
            this.name = blogger;
        }
    }

//    @RequestMapping("/article")
//    public String getAticleDefaultList() {
//        return "./article.html";
//    }

//    @RequestMapping("/")
//    public String aaa() {
//        if (!visitorFlag) return "../404.html";
//        else return "../index.html";
//    }

}
