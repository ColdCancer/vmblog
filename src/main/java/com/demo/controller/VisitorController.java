package com.demo.controller;

import com.demo.pojo.Blogger;
import com.demo.service.VisitorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class VisitorController {
    @Autowired()
    @Qualifier("visitorServiceImpl")
    private VisitorService visitorService;
    private ObjectMapper mapper = new ObjectMapper();
    private String name;
    private boolean visitorFlag;

    /*About Visitor to access a blog home*/
    @RequestMapping("/{account}/home")
    public String redirectBloogerHome(@PathVariable("account") String account) {
        return visitorService.existBlogger(account) ? "blogger-home" : "404";
    }

    @RequestMapping(value="/{account}/home/getMessageInHome", produces="application/json; charset=utf-8")
    @ResponseBody
    public String getMessageInHome(@PathVariable("account") String account) {
        HashMap<String, String> message = new HashMap<String, String>();
        Blogger blogger = visitorService.getBloggerByAccount(account);
        message.put("name", blogger.getName());
//        message.put("birthplace", blogger.getBirthplace);
        message.put("email", blogger.getEmail());
        message.put("sex", blogger.getSex());
//        message.put("education", blogger.getEducation());
//        message.put("postion", blogger.getPosition());
//        System.out.println(account + " - " + blogger.getSex());
        try {
            String result = mapper.writeValueAsString(message);
            System.out.println(result);
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        System.out.println("debug");
        return null;
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
