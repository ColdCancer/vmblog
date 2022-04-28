package com.demo.controller;

import com.demo.dao.BloggerDao;
import com.demo.entity.Article;
import com.demo.entity.ArticleSave;
import com.demo.entity.Blogger;
import com.demo.entity.ErComment;
import com.demo.service.ArticleSaveService;
import com.demo.service.ArticleService;
import com.demo.service.BloggerService;
import com.demo.service.ErCommentService;
import com.demo.utils.BaseTools;
import com.demo.utils.ResponseData;
import com.demo.utils.ResponseState;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.net.HttpResponse;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * (Blogger)表控制层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@RestController
public class BloggerController {
    @Resource
    private BloggerService bloggerService;
    @Resource
    private ArticleSaveService articleSaveService;
    @Resource
    private ArticleService articleService;
    @Resource
    private ErCommentService erCommentService;

    @PostMapping("web/passport/signup")
    public ResponseData signup(HttpSession session,
                               @Param("account")String account,
                               @Param("password")String password) {
        Blogger blogger = new Blogger();
        blogger.setErName(account);
        blogger.setErAccount(account);
        blogger.setSaSalt(BaseTools.UUID());
        // String real_password = password + "&" + blogger.getSaSalt();
        blogger.setErPassword(BaseTools.digest(password, blogger.getSaSalt()));

        blogger = bloggerService.insert(blogger);
        // String message = "Sign Up " + (blogger.getId() != null ? "Success." : "Failure.");

        if (blogger.getId() != null) {
            String src = BaseTools.getResourcesPath("blogger", "../");
            String dec = BaseTools.getResourcesPath(account, "../");
            BaseTools.folderCopy(src, dec);
            return new ResponseData(ResponseState.SUCCESS, null);
        } else {
            return new ResponseData(ResponseState.FAILURE, null);
        }

    }

    @GetMapping("api/passport/status")
    public ResponseData loginStatus(HttpSession session) {
//        System.out.println(session.getAttribute("account"));
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        if (blogger != null) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("account", blogger.getErAccount());
            return new ResponseData(ResponseState.SUCCESS, data);
        } else {
            return new ResponseData(ResponseState.FAILURE, null);
        }
    }

    @PostMapping("web/passport/signin")
    public ResponseData signin(HttpSession session,
                       @Param("account")String account,
                       @Param("password")String password,
                       @Param("remember")boolean remember) {
        if(bloggerService.checkAccountValid(account, password, remember)) {
            Blogger blogger = bloggerService.queryByAccount(account);
            session.setAttribute("blogger", blogger);
            session.setAttribute("last-login-date", blogger.getLastLoginDate());
            int flag = bloggerService.updateLastDate(blogger.getId(), new Date());
            return new ResponseData(0, "Sign In Success.", null);
        } else {
            return new ResponseData(-1, "Sign In Failure.", null);
        }
    }

    @GetMapping("/api/resources/{account}/profile-photo")
    public ResponseEntity<byte[]> entityBloggerProfilePhoto(HttpSession session,
                            @PathVariable("account") String account) {
        String base_path = session.getServletContext().getRealPath("/WEB-INF/blogger") +
                File.separator + account + File.separator + "resources" + File.separator;
        File file = new File(base_path + "blogger-head-image.jpg");
//        System.out.println(base_path + "blogger-head-image.jpg");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        ResponseEntity<byte[]> responseEntity = null;
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<byte[]>(null, headers, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @PostMapping("/web/passport/signout")
    public ResponseData signout(HttpSession session) {
        if (session.getAttribute("blogger") != null) {
            session.invalidate();
            return new ResponseData(ResponseState.SUCCESS, null);
        } else {
            return new ResponseData(ResponseState.EMPTY, null);
        }
    }

    @GetMapping("/web/api/blogger/baseInfo")
    public ResponseData getBaseInfo(HttpSession session) {
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        if (blogger == null) return new ResponseData(ResponseState.FAILURE, null);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("account", blogger.getErAccount());
        data.put("name", blogger.getErName());
        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @GetMapping("/web/api/information")
    public ResponseData getBloggerInformation(HttpSession session) {
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        Map<String, Object> data = new HashMap<String, Object>();
        // data: {
        //     'name', 'account', 'register', 'current',
        //     'article':{}, 'comment':{}
        // }
        data.put("name", blogger.getErName());
        data.put("account", blogger.getErAccount());
        data.put("register", BaseTools.toString(blogger.getRegisterDate()));
        data.put("current", BaseTools.toString(blogger.getLastLoginDate()));
        Map<String, Object> article_json = new HashMap<String, Object>();
        Map<String, Object> comment_json = new HashMap<String, Object>();
        int article_total = 5, comment_total = 5;
        List<ArticleSave> articleSaveList = articleSaveService.queryByBloggerIdLimit(blogger.getId(), article_total);
        if (articleSaveList.size() != 0) {
            for (int i = 0; i < articleSaveList.size(); i++) {
                Map<String, Object> mapper = new HashMap<String, Object>();
                ArticleSave articleSave = articleSaveList.get(i);
                Article article = articleService.queryById(articleSave.getArticleId());
                mapper.put("title", article.getTitle());
                mapper.put("segment", article.getSegment());
                Date date = articleSave.getUpdateDate();
                mapper.put("updateDate", BaseTools.toString(date));
                mapper.put("type", article.getPostDate() == null ?
                        "Saved" : "Posted");
                mapper.put("topRank", article.getTopRank());
                article_json.put("" + i, mapper);
            }
            data.put("article", article_json);
            List<ErComment> commentList = erCommentService.queryByBloggerIdLimit(blogger.getId(), comment_total);
            if (commentList.size() != 0) {
                for (int i = 0; i < commentList.size(); i++) {
                    Map<String, Object> mapper = new HashMap<String, Object>();
                    ErComment comment = commentList.get(i);
                    Blogger from = bloggerService.queryById(comment.getFromBloggerId());
                    Blogger to = bloggerService.queryById(comment.getToBloggerId());
                    Article article = articleService.queryById(comment.getArticleId());
                    String topic = comment.getTopicContent();
                    String date = BaseTools.toString(comment.getPostDate());
                    mapper.put("from", from.getErName());
                    mapper.put("to", to.getErName());
                    mapper.put("article", article.getTitle());
                    mapper.put("comment", topic);
                    mapper.put("postDate", date);
                    comment_json.put("" + i, mapper);
                }
            }
            data.put("comment", comment_json);
        }
        return new ResponseData(ResponseState.SUCCESS, data);
    }
}