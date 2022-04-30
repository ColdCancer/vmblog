package com.demo.controller;

import com.demo.entity.Article;
import com.demo.entity.Blogger;
import com.demo.entity.ErComment;
import com.demo.entity.Mediae;
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
                to_blogger.getId(), article.getId(), topic, new Date(), null, null);
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
                article.getId(), topic, new Date(), null, parent.getId()));
        return new ResponseData(ResponseState.SUCCESS, null);
    }

    @GetMapping("/web/api/comment/items/page/{number}")
    public ResponseData getCommentsByPageNumber(HttpSession session,
                        @PathVariable("number") Integer number) {
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        if (number <= 0) return new ResponseData(ResponseState.EMPTY, null);
        int article_size = 12, begin_index = (number - 1) * article_size;
        List<ErComment> commentList = erCommentService.queryAllByBloggerAndLimit(blogger.getId(), article_size, begin_index);
        if (commentList.size() == 0) return new ResponseData(ResponseState.EMPTY, null);

        Map<String, Object> data = new HashMap<String, Object>();
        for (int i = 0; i < commentList.size(); i++) {
            ErComment comment = commentList.get(i);
            Map<String, Object> mapper = new HashMap<String, Object>();

            Article article = articleService.queryById(comment.getArticleId());
            Blogger to_blogger = bloggerService.queryById(comment.getToBloggerId());

            mapper.put("id", comment.getId());
            mapper.put("article", article.getSegment());
            mapper.put("to", to_blogger.getErName());
            mapper.put("main", comment.getTopicContent());
            mapper.put("post", BaseTools.toString(comment.getPostDate()));
            data.put("" + (i + 1), mapper);
        }

        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @PostMapping("/web/api/comment/deleteComment/{id}")
    public ResponseData deleteComment(HttpSession session,
                              @PathVariable("id") String id) {
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        ErComment comment = erCommentService.queryById(Integer.parseInt(id));
        if (!comment.getToBloggerId().equals(blogger.getId())) {
            return new ResponseData(ResponseState.FAILURE, null);
        }
        int flag = erCommentService.logicDelete(comment.getId());
        return new ResponseData(ResponseState.SUCCESS, null);
    }

    @GetMapping("/api/comment/current")
    public ResponseData getCurrentComment() {
        int number = 6;
        List<ErComment> comments = erCommentService.queryByCurrent(number);
        if (comments.size() == 0) return new ResponseData(ResponseState.EMPTY, null);
        Map<String, Object> data = new HashMap<String, Object>();
        for (int i = 0; i < comments.size(); i++) {
            ErComment comment = comments.get(i);
            Map<String, Object> mapper = new HashMap<String, Object>();
            Blogger from_blogger = bloggerService.queryById(comment.getFromBloggerId());
            Blogger to_blogger = bloggerService.queryById(comment.getToBloggerId());
            Article article = articleService.queryById(comment.getArticleId());
            Blogger by_blogger = bloggerService.queryById(article.getBloggerId());
            String url = "/article/" + by_blogger.getErAccount() + "/" + article.getLinkName();
            mapper.put("url", url);
            mapper.put("title", article.getTitle());
            mapper.put("from", from_blogger.getErName());
            mapper.put("to", to_blogger.getErName());
            mapper.put("comment", comment.getTopicContent());
            data.put("" + i, mapper);
        }
        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @GetMapping("/api/comment/blogger/current")
    public ResponseData getBloggerCurrentComment(@RequestParam("account") String account) {
        Blogger blogger = bloggerService.queryByAccount(account);
        if (blogger == null) return new ResponseData(ResponseState.FAILURE, null);
        int number = 6;
        List<ErComment> comments = erCommentService.queryCurrentByBloggerId(blogger.getId(), number);
        if (comments.size() == 0) return new ResponseData(ResponseState.EMPTY, null);
        Map<String, Object> data = new HashMap<String, Object>();
        for (int i = 0; i < comments.size(); i++) {
            ErComment comment = comments.get(i);
            Map<String, Object> mapper = new HashMap<String, Object>();
            Blogger from_blogger = bloggerService.queryById(comment.getFromBloggerId());
            Blogger to_blogger = bloggerService.queryById(comment.getToBloggerId());
            Article article = articleService.queryById(comment.getArticleId());
            Blogger by_blogger = bloggerService.queryById(article.getBloggerId());
            String url = "/article/" + by_blogger.getErAccount() + "/" + article.getLinkName();
            mapper.put("url", url);
            mapper.put("title", article.getTitle());
            mapper.put("from", from_blogger.getErName());
            mapper.put("to", to_blogger.getErName());
            mapper.put("comment", comment.getTopicContent());
            data.put("" + i, mapper);
        }
        return new ResponseData(ResponseState.SUCCESS, data);
    }

}