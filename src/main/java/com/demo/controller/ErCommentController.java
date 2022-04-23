package com.demo.controller;

import com.demo.entity.Article;
import com.demo.entity.Blogger;
import com.demo.entity.ErComment;
import com.demo.service.ArticleService;
import com.demo.service.BloggerService;
import com.demo.service.ErCommentService;
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
 * (ErComment)表控制层
 *
 * @author vmice
 * @since 2022-04-23 14:39:56
 */
@RestController
public class ErCommentController {
    @Resource
    private ErCommentService erCommentService;
    @Resource
    private ArticleService articleService;
    @Resource
    private BloggerService bloggerService;

    @PostMapping("/web/api/comment/addComment")
    public ResponseData addComment(HttpSession session,
                       @RequestParam("account") String account,
                       @RequestParam("link") String link,
                       @RequestParam("comment") String topic) {
        Blogger from_blogger = (Blogger) session.getAttribute("blogger");
        Blogger to_blogger = bloggerService.queryByAccount(account);
        Article article = articleService.queryByAccoutAndLink(to_blogger.getId(), link);

        ErComment comment = new ErComment(null, from_blogger.getId(),
                to_blogger.getId(), article.getId(), topic, new Date(), null);
        comment = erCommentService.insert(comment);
        if (comment.getId() != null) {
            return new ResponseData(ResponseState.SUCCESS, null);
        } else {
            return new ResponseData(ResponseState.FAILURE, null);
        }
    }

    @GetMapping("/api/comment/list")
    public ResponseData getCommentList(HttpSession session,
                       @RequestParam("account") String account,
                       @RequestParam("link") String link) {
        Blogger blogger = bloggerService.queryByAccount(account);
        if (blogger == null) return new ResponseData(ResponseState.FAILURE, null);
        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        if (article == null) return new ResponseData(ResponseState.FAILURE, null);
        List<ErComment> commentList = erCommentService.queryByArticleId(article.getId());
        if (commentList.size() == 0) return new ResponseData(ResponseState.EMPTY, null);
        Map<String, Object> data = new HashMap<String, Object>();
        for (ErComment comment : commentList) {
            // System.out.println(comment);
            Map<String, Object> item = new HashMap<String, Object>();
            Blogger blogger1 = bloggerService.queryById(comment.getFromBloggerId());
            item.put("fromName", blogger1.getErName());
            item.put("from", blogger1.getErAccount());
            Blogger blogger2 = bloggerService.queryById(comment.getToBloggerId());
            item.put("toName", blogger2.getErName());
            item.put("to", blogger2.getErAccount());
            item.put("date", BaseTools.toString(comment.getPostDate()));
            item.put("topic", comment.getTopicContent());
            item.put("parent", comment.getParentId());
            data.put("" + comment.getId(), item);
        }
        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @PostMapping("/web/api/comment/attachComment")
    public ResponseData attachComment(HttpSession session,
                      @RequestParam("parent-id") String parentId,
                      @RequestParam("article-account") String articleAccount,
                      @RequestParam("article-link") String articleLink,
                      @RequestParam("to-blogger") String toAccount,
                      @RequestParam("topic") String topic) {
        if (parentId == null) return new ResponseData(ResponseState.EMPTY, null);
        Blogger articleBloger = bloggerService.queryByAccount(articleAccount);
        Article article = articleService.queryByAccoutAndLink(articleBloger.getId(), articleLink);
        if (article == null) return new ResponseData(ResponseState.EMPTY, null);
        Blogger fromBlogger = (Blogger) session.getAttribute("blogger");
        Blogger toBlogger = bloggerService.queryByAccount(toAccount);
        if (toBlogger == null) return new ResponseData(ResponseState.EMPTY, null);
        ErComment parent = erCommentService.queryById(Integer.parseInt(parentId));
        if (parent == null) return new ResponseData(ResponseState.EMPTY, null);
        erCommentService.insert(new ErComment(null, fromBlogger.getId(), toBlogger.getId(),
                article.getId(), topic, new Date(), parent.getId()));
        return new ResponseData(ResponseState.SUCCESS, null);
    }
}