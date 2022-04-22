package com.demo.controller;

import com.demo.entity.*;
import com.demo.service.*;
import com.demo.utils.BaseTools;
import com.demo.utils.ResponseData;
import com.demo.utils.ResponseState;
import netscape.security.UserTarget;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.*;
import java.util.*;

/**
 * (Article)表控制层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@RestController
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleStateService articleStateService;
    @Resource
    private BloggerService bloggerService;
    @Resource
    private MediaeService mediaeService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private CategoryLinkService categoryLinkService;
    @Resource
    private ArticleSaveService articleSaveService;

    @GetMapping("/api/article/{blogger}/{link}")
    public ResponseData getArticleByLink(Integer id) {
//        return this.articleService.queryById(id);
        return new ResponseData(0, "article", null);
    }

    @PostMapping("/web/api/article/addCover")
    public ResponseData addCover(
            @RequestParam("cover") MultipartFile coverFile,
            @RequestParam("account") String account,
            @RequestParam("link") String link,
            HttpSession session) {
        String oFilename = coverFile.getOriginalFilename();
        int lastIndex = oFilename.lastIndexOf(".");

        String file_type = oFilename.substring(lastIndex);
        String md_digest = BaseTools.randomStr(18);
        String md_name = BaseTools.randomStr(12);

        String file_name = BaseTools.digest(account, md_digest);
        String file_path = BaseTools.getImagePath(file_name + file_type);

        try {
            File file = new File(file_path);
            coverFile.transferTo(file);
        } catch (IOException e) {
            return new ResponseData(ResponseState.FAILURE, null);
        }

        Blogger blogger = bloggerService.queryByAccount(account);
        int id = blogger.getId();

        Mediae mediae = new Mediae(null, id, md_name, md_digest, file_type, null);
        mediae = mediaeService.insert(mediae);

        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        article.setCoverId(mediae.getId());
        articleService.update(article);

        return new ResponseData(ResponseState.SUCCESS, null);
    }

    @GetMapping("/api/article/page/{number}")
    public ResponseData getArticlesByPageNumber(HttpSession session,
                    @PathVariable("number") Integer number) {
        if (number <= 0) return new ResponseData(ResponseState.EMPTY, null);
        int article_size = 12, begin_index = (number - 1) * article_size;
        List<Article> articleList = articleService.queryAllByLimit(article_size, begin_index);
        if (articleList.size() == 0) return new ResponseData(ResponseState.EMPTY, null);

        Map<String, Object> data = new HashMap<String, Object>();
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            Map<String, Object> mapper = new HashMap<String, Object>();

            Blogger blogger = bloggerService.queryById(article.getBloggerId());
            String account = blogger.getErAccount();
            if (article.getCoverId() != null) {
                Mediae mediae = mediaeService.queryById(article.getCoverId());
                String file_name = BaseTools.digest(account, mediae.getMdDigest());
                //  url: /api/resource/images/xxx.type
                String cover_path = "/api/resource/images/" + file_name + mediae.getFlagType();
                mapper.put("cover", cover_path);
            } else {
                mapper.put("cover", "#");
            }
            // cover_path: /api/meidae/images/article-cover-default.jpg
            // String file_path = BaseTools.getMDPath(account, article.getFileName());
            // File file = new File(file_path + ".md");
            // String segment = null;
            // try {
            //     byte[] bytes = FileUtils.readFileToByteArray(file);
            //     // len(segment) = len(bytes) * 2 +- 1;
            //     segment = new String(bytes, "utf-8");
            //     segment = BaseTools.delHTMLTag(segment);
            //     int length = Math.min(100, segment.length());
            //     segment = segment.substring(0, length);
            // } catch (IOException e) {
            //     // e.printStackTrace();
            //     segment = "no content";
            // }

            Date date = article.getPostDate();
            if (article.getUpdateDate() != null) {
                date = article.getUpdateDate();
            }

            int like_count = articleStateService.queryCountByArticleId(article.getId(), "like");
            int dislike_count = articleStateService.queryCountByArticleId(article.getId(), "dislike");

            mapper.put("title", article.getTitle());
            mapper.put("segmental", article.getSegment());
            mapper.put("post", BaseTools.subDate(date, new Date()));
            mapper.put("blogger", blogger.getErName());
            mapper.put("views", article.getVisCount());
            mapper.put("like", like_count);
            mapper.put("dislike", dislike_count);
            mapper.put("link", article.getLinkName());
            data.put("" + (i + 1), mapper);
        }

        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @GetMapping("/web/api/article/md/content")
    public ResponseEntity<byte[]> getEditArticleContent(HttpSession session,
                            @RequestParam("account") String account,
                            @RequestParam("link") String link) {
        Blogger blogger = bloggerService.queryByAccount(account);
        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        String file_path = BaseTools.getMDPath(account, article.getFileName());

        ResponseEntity<byte[]> responseEntity = null;
        try {
            File file = new File(file_path + ".save");
            byte[] bytes = FileUtils.readFileToByteArray(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",
                    "attachment;filename=markdown.md");
            // headers.setContentType(MediaType.TEXT_HTML);
            HttpStatus httpStatus = HttpStatus.OK;
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, httpStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/api/article/md/content")
    public ResponseEntity<byte[]> getViewArticleContent(HttpSession session,
                            @RequestParam("account") String account,
                            @RequestParam("link") String link) {
        Blogger blogger = bloggerService.queryByAccount(account);
        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        String file_path = BaseTools.getMDPath(account, article.getFileName());

        ResponseEntity<byte[]> responseEntity = null;
        try {
            File file = new File(file_path + ".md");
            byte[] bytes = FileUtils.readFileToByteArray(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",
                    "attachment;filename=markdown.md");
            // headers.setContentType(MediaType.TEXT_HTML);
            HttpStatus httpStatus = HttpStatus.OK;
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, httpStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    @PostMapping("/web/api/article/delete/list")
    public ResponseData deleteArticles(HttpSession session,
            @RequestParam(value="deleteList") String[] links) {
        if (links == null) return new ResponseData(ResponseState.EMPTY, null);

        Map<String, Object> data = new HashMap<String, Object>();
        // String account = (String) session.getAttribute("account");
        // Blogger blogger = bloggerService.queryByAccount(account);
        Blogger blogger = (Blogger) session.getAttribute("blogger");

        int count = 0;
        for (int i = 0; i < links.length; i++) {
            Map<String, Object> mapper = new HashMap<String, Object>();
            Article article = articleService.queryByAccoutAndLink(blogger.getId(), links[i]);
            String file_path = BaseTools.getMDPath(blogger.getErAccount(), article.getFileName());
            int flag = articleSaveService.deleteByArticleId(article.getId());
            if (flag != 0) {
                flag = articleService.deleteByAccountAndLink(blogger.getId(), links[i]);
            }
            BaseTools.deleteMarkdown(file_path);

            mapper.put("link", links[i]);
            mapper.put("delete", flag);

            if (flag == 1) count += 1;
            data.put("" + (i + 1), mapper);
        }
        data.put("success", count);

        if (count == 0) {
            return new ResponseData(ResponseState.EMPTY, null);
        } else {
            return new ResponseData(ResponseState.SUCCESS, data);
        }
    }

    @GetMapping("/web/api/article/editInfo")
    public ResponseData getArticleEditInfo(HttpSession session,
                    @RequestParam("link") String link) {
        // String account = (String) session.getAttribute("account");
        // Blogger blogger = bloggerService.queryByAccount(account);
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        if (article == null) return new ResponseData(ResponseState.EMPTY, null);
        ArticleSave articleSave = articleSaveService.queryByArticleId(article.getId());
        if (articleSave == null) return new ResponseData(ResponseState.EMPTY, null);

        Map<String, Object> data = new HashMap<String, Object>();
        String updateDate = BaseTools.toString(article.getUpdateDate());
        if ("-".equals(updateDate)) {
            updateDate = BaseTools.toString(article.getPostDate());
        }

        data.put("title", article.getTitle());
        data.put("account", blogger.getErAccount());
        data.put("article-link", link);
        data.put("update-date", updateDate);
        data.put("top-rank", article.getTopRank());

        // CategoryLink categoryLink = categoryLinkService.queryByArticleId(article.getId());
        if (articleSave.getCategoryId() != null) {
            Category category = categoryService.queryById(articleSave.getCategoryId());
            data.put("category", category.getTypeName());
        } else {
            data.put("category", null);
        }

        // List<String> categories = new ArrayList<String>();
        // categories.add("one");
        // categories.add("two");
        // data.put("categories", categories);

        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @GetMapping("/web/api/article/items/page/{number}")
    public ResponseData getArticleItemsByPageNumber(HttpSession session,
                    @PathVariable("number") Integer number) {
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        int page_size = 10, begin_index = (number - 1) * page_size;
        List<Article> articleList = articleService.queryAllByBloggerIdAndLimit(
                blogger.getId(), page_size, begin_index);

        if (articleList == null) return new ResponseData(ResponseState.EMPTY, null);

        Map<String, Object> data = new HashMap<String, Object>();
        for (int i = 0; i < articleList.size(); i++) {
            Map<String, Object> mapper = new HashMap<String, Object>();
            Article article = articleList.get(i);

            mapper.put("id", begin_index + i + 1);
            mapper.put("title", article.getTitle());
            mapper.put("postDate", BaseTools.toString(article.getPostDate()));
            mapper.put("updateDate", BaseTools.toString(article.getUpdateDate()));
            mapper.put("link", article.getLinkName());

            data.put("" + (i + 1), mapper);
        }

        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @PostMapping("/web/api/article/addArticle")
    public ResponseData addArticle(HttpSession session,
                       @RequestParam("title") String title,
                       @RequestParam("article") MultipartFile article,
                       @RequestParam("postDate") String postDate,
                       @RequestParam("category") String category,
                       @RequestParam("top") String top) {
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        String link_name = BaseTools.randomStr(12);
        String file_name = BaseTools.randomStr(20);
        String file_path = BaseTools.getMDPath(blogger.getErAccount(), file_name);
        // String backup_file_path = file_path + ".save";
        // Blogger blogger = bloggerService.queryByAccount(account);

        // ResponseData responseData = null;
        try {
            File file = new File(file_path + ".md");
            // System.out.println(file_path);
            article.transferTo(file);
            File backup_file = new File(file_path + ".save");
            article.transferTo(backup_file);

            byte[] bytes = FileUtils.readFileToByteArray(file);
            // len(segment) = len(bytes) * 2 +- 1;
            String segment = new String(bytes, "utf-8");
            segment = BaseTools.delHTMLTag(segment);
            segment = segment.substring(0, Math.min(30, segment.length()));
            // String content = BaseTools.getSegment()
            Article article_ojb = new Article(null, blogger.getId(),
                    null, title, link_name, file_name, segment,
                    BaseTools.toDate(postDate), null,
                    Integer.parseInt(top), 0);
            article_ojb = articleService.insert(article_ojb);
            ArticleSave articleSave = new ArticleSave(null, blogger.getId(),
                    article_ojb.getId(), null, title, new Date(),
                    Integer.parseInt(top), null);
            articleSave = articleSaveService.insert(articleSave);
            Map<String, Object> data = new HashMap<String, Object>();

            Category category_ojb = categoryService.queryByBloggerIdAndTypeName(blogger.getId(), category);
            if (category_ojb == null) {
                categoryLinkService.insert(new CategoryLink(
                        null, article_ojb.getId(), null));
                data.put("category", null);
            }
            else {
                articleSave.setCategoryId(category_ojb.getId());
                articleSaveService.update(articleSave);
                categoryLinkService.insert(new CategoryLink(
                        null, article_ojb.getId(), category_ojb.getId()));
                data.put("category", category);
            }

            data.put("account", blogger.getErAccount());
            data.put("article-link", link_name);
            data.put("update-date", postDate);

            return new ResponseData(ResponseState.SUCCESS, data);
        } catch (IOException e) {
            return new ResponseData(ResponseState.FAILURE, null);
        }
    }

    @PostMapping("/web/api/article/addBackupArticle")
    public ResponseData addBackupArticle(HttpSession session,
                       @RequestParam("title") String title,
                       @RequestParam("article") MultipartFile article,
                       @RequestParam("postDate") String postDate,
                       @RequestParam("category") String category,
                       @RequestParam("top") String top) {
        // String account = (String) session.getAttribute("account");
        // Blogger blogger = bloggerService.queryByAccount(blogger.getErAccount());
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        String link_name = BaseTools.randomStr(12);
        String file_name = BaseTools.randomStr(20);
        String file_path = BaseTools.getMDPath(blogger.getErAccount(), file_name) + ".save";

        try {
            File file = new File(file_path);
            article.transferTo(file);


            int topRank = Integer.parseInt(top);
            Article article_obj = new Article(null, blogger.getId(),
                    null, title, link_name, file_name, null,
                    null, null, 0, 0);
            article_obj = articleService.insert(article_obj);

            ArticleSave articleSave = new ArticleSave(null, blogger.getId(),
                    article_obj.getId(), null, title, new Date(), topRank, null);

            Map<String, Object> data = new HashMap<String, Object>();
            Category category_ojb = categoryService.queryByBloggerIdAndTypeName(blogger.getId(), category);
            if (category_ojb == null) data.put("category", null);
            else {
                articleSave.setCategoryId(category_ojb.getId());
                // articleSaveService.update(articleSave);
                articleSave.setCategoryId(category_ojb.getId());
                categoryLinkService.insert(new CategoryLink(
                        null, article_obj.getId(), category_ojb.getId()));
                data.put("category", category);
            }
            articleSaveService.insert(articleSave);

            data.put("account", blogger.getErAccount());
            data.put("article-link", link_name);
            data.put("update-date", postDate);

            return new ResponseData(ResponseState.SUCCESS, data);
        } catch (IOException e) {
            return new ResponseData(ResponseState.FAILURE, null);
        }
    }

    @PostMapping("/web/api/article/updateArticle")
    public ResponseData updateArticle(HttpSession session,
                       @RequestParam("article") MultipartFile article,
                       @RequestParam("title") String title,
                       @RequestParam("link") String link,
                       @RequestParam("postDate") String postDate,
                       @RequestParam("category") String category,
                       @RequestParam("top") String top) {
        Blogger blogger = (Blogger) session.getAttribute("blogger");

        Article article_ojb = articleService.queryByAccoutAndLink(blogger.getId(), link);
        if (article_ojb == null) return new ResponseData(ResponseState.EMPTY, null);
        ArticleSave articleSave = articleSaveService.queryByArticleId(article_ojb.getId());
        if (articleSave == null) return new ResponseData(ResponseState.EMPTY, null);

        if (article_ojb.getPostDate() == null) {
            articleService.updateDatesById(article_ojb.getId(), new Date());
        }
        article_ojb.setTitle(title);
        article_ojb.setUpdateDate(BaseTools.toDate(postDate));
        article_ojb.setTopRank(Integer.parseInt(top));
        articleSave.setTitle(title);
        articleSave.setUpdateDate(BaseTools.toDate(postDate));
        articleSave.setTopRank(Integer.parseInt(top));

        if (category == null || "".equals(category)) {
            categoryLinkService.update(new CategoryLink(null, article_ojb.getId(), null));
            articleSave.setCategoryId(null);
        } else {
            Category category_ojb = categoryService.queryByBloggerIdAndTypeName(blogger.getId(), category);
            if (category_ojb != null) {
                categoryLinkService.update(new CategoryLink(null, article_ojb.getId(), category_ojb.getId()));
                articleSave.setCategoryId(category_ojb.getId());
            }
        }

        String file_path = BaseTools.getMDPath(blogger.getErAccount(), article_ojb.getFileName());
        String backup_path = file_path + ".save";

        File file = new File(file_path + ".md");
        File backcup_file = new File(backup_path);
        try {
            article.transferTo(file);
            article.transferTo(backcup_file);

            byte[] bytes = FileUtils.readFileToByteArray(file);
            String segment = new String(bytes, "utf-8");
            segment = BaseTools.delHTMLTag(segment);
            segment = segment.substring(0, Math.min(30, segment.length()));
            article_ojb.setSegment(segment);
            articleService.update(article_ojb);
            articleSaveService.update(articleSave);

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("account", blogger.getErAccount());
            data.put("article-link", link);
            data.put("update-date", postDate);
            return new ResponseData(0, "success", data);
        } catch (IOException e) {
            return new ResponseData(-1, "update failure", null);
        }
    }

    @PostMapping("/web/api/article/updateBackupArticle")
    public ResponseData updateBackupArticle(HttpSession session,
                       @RequestParam("article") MultipartFile article,
                       @RequestParam("title") String title,
                       @RequestParam("link") String link,
                       @RequestParam("postDate") String postDate,
                       @RequestParam("category") String category,
                       @RequestParam("top") String top) {
        // String account = (String) session.getAttribute("account");
        // Blogger blogger = bloggerService.queryByAccount(account);
        Blogger blogger = (Blogger) session.getAttribute("blogger");

        Article article_ojb = articleService.queryByAccoutAndLink(blogger.getId(), link);
        ArticleSave articleSave = articleSaveService.queryByArticleId(article_ojb.getId());
        articleSave.setTitle(title);
        articleSave.setUpdateDate(new Date());
        articleSave.setTopRank(Integer.parseInt(top));

        Category category_ojb = categoryService.queryByBloggerIdAndTypeName(blogger.getId(), category);
        if (category_ojb != null) {
            articleSave.setCategoryId(category_ojb.getId());
        } else {
            articleSave.setCoverId(null);
        }
        articleSaveService.update(articleSave);
        // article_ojb.setTitle(title);
        // article_ojb.setFlagType("saved");
        // article_ojb.setUpdateDate(BaseTools.toDate(postDate));
        // articleService.update(article_ojb);

        String file_path = BaseTools.getMDPath(blogger.getErAccount(), article_ojb.getFileName());

//        File file = new File(base_path + article_ojb.getFileName());
        File backcup_file = new File(file_path + ".save");
        try {
//            article.transferTo(file);
            article.transferTo(backcup_file);

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("account", blogger.getErAccount());
            data.put("article-link", link);
            data.put("update-date", postDate);

            return new ResponseData(ResponseState.SUCCESS, data);
        } catch (IOException e) {
            return new ResponseData(ResponseState.FAILURE, null);
        }
    }

    @GetMapping("/api/article/info")
    public ResponseData getArticleInfo(HttpSession session,
                               @RequestParam("link") String link,
                               @RequestParam("account") String account) {
        // System.out.println(account + " : " + link);
        Blogger blogger = bloggerService.queryByAccount(account);
        if (blogger == null) return new ResponseData(ResponseState.EMPTY, null);
        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        if (article == null) return new ResponseData(ResponseState.EMPTY, null);

        int like_count = articleStateService.queryCountByArticleId(article.getId(), "like");
        int dislike_count = articleStateService.queryCountByArticleId(article.getId(), "dislike");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", article.getTitle());
        data.put("postDate", BaseTools.toString(article.getPostDate()));
        data.put("likeCount", like_count);
        data.put("dislikeCount", dislike_count);

        int flag = articleService.addVisCount(article.getId());
        data.put("vistor", flag);
        data.put("visCount", article.getVisCount() + flag);

        String auth_account = (String) session.getAttribute("account");
        Blogger auth = bloggerService.queryByAccount(auth_account);
        if (auth == null) {
            data.put("state", "");
        } else {
            ArticleState articleState = articleStateService.queryByIds(auth.getId(), article.getId());
            data.put("state", articleState == null ? "" : articleState.getOnState());
        }

        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @PostMapping("/api/article/operation/state")
    public ResponseData operateArticleState(HttpSession session,
                        @RequestParam("account") String account,
                        @RequestParam("link") String link,
                        @RequestParam("state") String state) {
        // String self_account = (String) session.getAttribute("account");
        Blogger self = (Blogger) session.getAttribute("blogger");
        if (self == null) return new ResponseData(ResponseState.FAILURE, null);

        // Blogger self = bloggerService.queryByAccount(self_account);
        Blogger blogger = bloggerService.queryByAccount(account);
        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        // int like = articleStateService.queryCountByArticleId(article.getId(), "like");
        // int dislike = articleStateService.queryCountByArticleId(article.getId(), "dislike");
        // 0:(0,0) - 1:(1,0) - 2:(1,-1)
        int flag = articleStateService.modifyState(self.getId(), article.getId(), state);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("like", flag);
        return new ResponseData(ResponseState.SUCCESS, data);
    }
}