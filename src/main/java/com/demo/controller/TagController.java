package com.demo.controller;

import com.demo.pojo.Article;
import com.demo.pojo.Blogger;
import com.demo.pojo.Tag;
import com.demo.service.BloggerService;
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
public class TagController {
    @Autowired()
    @Qualifier("bloggerServiceImpl")
    private BloggerService bloggerService;

    /*=====================About foreword tag web=======================*/
    @GetMapping("/admin/tag")
    public String forewordAdminTag() {
        return "admin/admin-tag";
    }

    /*==================About article page message====================*/
    @SneakyThrows
    @GetMapping("/admin/tag/getTagList")
    @ResponseBody
    public String getTagList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        HashMap<String, Object> message = new HashMap<String, Object>();

        // add admin self information
        message.put("account", blogger.getAccount());

        // add article other information
        List<Tag> tags = bloggerService.getTagByAccount(blogger.getAccount());
        message.put("total", tags.size());
//        Article reccent = bloggerService.getReccentPostArticle(blogger.getAccount());
//        message.put("linkCount", reccent.getPost_time().toString());
        int linkCount = 0;

        // add article content information
        List<HashMap<String, Object>> tagList = new ArrayList<HashMap<String, Object>>();
        for (Tag tag : tags) {
            HashMap<String, Object> tagJson = new HashMap<String, Object>();
            tagJson.put("name", tag.getName());
//            int count = bloggerService.getTagLinkCount(tag);
            int count = 1;
            linkCount += count;
            tagJson.put("count", count);
            tagList.add(tagJson);
        }
        message.put("linkCount", linkCount);
        message.put("tags", tagList);

        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(message);
    }

}
