package com.demo.controller;

import com.demo.entity.Blogger;
import com.demo.entity.GlobalChat;
import com.demo.service.BloggerService;
import com.demo.service.GlobalChatService;
import com.demo.utils.BaseTools;
import com.demo.utils.ResponseData;
import com.demo.utils.ResponseState;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (GlobalChat)表控制层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@RestController
public class GlobalChatController {
    @Resource
    private GlobalChatService globalChatService;
    @Resource
    private BloggerService bloggerService;

    @GetMapping("/api/chat/currentChat")
    public ResponseData getCurrentChat(HttpSession session) {
        Blogger self = (Blogger) session.getAttribute("blogger");
        int total = 30;
        List<GlobalChat> chatList = globalChatService.queryByCount(total);
        if (chatList.size() == 0) return new ResponseData(ResponseState.EMPTY, null);
        Map<String, Object> data = new HashMap<String, Object>();
        for (int i = 0; i < chatList.size(); i++) {
            GlobalChat chat = chatList.get(i);
            Blogger blogger = bloggerService.queryById(chat.getBloggerId());
            Map<String, Object> mapper = new HashMap<String, Object>();
            mapper.put("name", blogger.getErName());
            mapper.put("photo", "/api/resources/" + blogger.getErAccount() + "/profile-photo");
            mapper.put("date", BaseTools.toString(chat.getPostDate()));
            mapper.put("topic", chat.getTopicContent());
            mapper.put("self", self != null && blogger.getId().equals(self.getId()));
            data.put("" + i, mapper);
        }
        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @PostMapping("/web/api/chat/addChat")
    public ResponseData addOneChat(HttpSession session,
                           @RequestParam("topic") String topic) {
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        GlobalChat chat = new GlobalChat(null, blogger.getId(), new Date(), topic);
        chat = globalChatService.insert(chat);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("flag", chat.getId() != null);
        return new ResponseData(ResponseState.SUCCESS, data);

    }
}