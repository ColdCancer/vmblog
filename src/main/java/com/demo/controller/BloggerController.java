package com.demo.controller;

import com.demo.pojo.Blogger;
import com.demo.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BloggerController {
    @Autowired()
    @Qualifier("bloggerServiceImpl")
    private BloggerService bloggerService;

    @PostMapping("/checkBloggerByForm")
    @ResponseBody
    public String checkBloggerByForm(HttpSession session, String account, String password) {
        boolean flag = bloggerService.checkBlogger(account, password);
        if (flag) {
            session.setAttribute("blogger", bloggerService.getBloggerByAccount(account));
            return "true";
        } else {
            return "false";
        }
    }
}
