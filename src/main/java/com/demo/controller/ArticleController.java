package com.demo.controller;

import com.demo.entity.Article;
import com.demo.entity.Blogger;
import com.demo.entity.Mediae;
import com.demo.service.ArticleService;
import com.demo.service.BloggerService;
import com.demo.service.MediaeService;
import com.demo.utils.BaseTools;
import com.demo.utils.ResponseData;
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
    private BloggerService bloggerService;
    @Resource
    private MediaeService mediaeService;

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
//        System.out.println(coverFile);
//        System.out.println(account + " : " + link);

        String oFilename = coverFile.getOriginalFilename();
//        System.out.println(oFilename);
        int lastIndex = oFilename.lastIndexOf(".");
        String fileType = oFilename.substring(lastIndex);
        String mdDigest = BaseTools.randomStr(18);
        String fileName = DigestUtils.shaHex(account + "&" + mdDigest);

        String base_path = session.getServletContext().getRealPath("/WEB-INF/mediae/images");
        String file_path = base_path + File.separator + fileName + fileType;
//        System.out.println(file_path);
        File directory = new File(base_path);
        if (!directory.exists()) { directory.mkdirs(); }

        File file = new File(file_path);
        try {
            coverFile.transferTo(file);
        } catch (IOException e) {
            return new ResponseData(-1, "upload cover error.", null);
        }

        ResponseData responseData = null;

        Blogger blogger = bloggerService.queryByAccount(account);
        String mdName = BaseTools.randomStr(12);
        Mediae mediae = new Mediae(null, blogger.getId(), mdName, mdDigest, fileType, null);
//        System.out.println(mediae);
        mediae = mediaeService.insert(mediae);
//        System.out.println(mediae);
        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        article.setCoverId(mediae.getId());
        articleService.update(article);

//        System.out.println(mediae.getId());
        return new ResponseData(0, "cover", null);
    }

    @GetMapping("/api/article/page/{number}")
    public ResponseData getArticlesByPageNumber(HttpSession session,
                    @PathVariable("number") Integer number) {
        int number_of_one_page = 12;
        int begin_index = (number - 1) * number_of_one_page;
        List<Article> articleList = articleService.queryAllByLimit(
                number_of_one_page, begin_index);
        ResponseData responseData = null;

        if (articleList != null) {
            Map<String, Object> data = new HashMap<String, Object>();
            for (int i = 0; i < articleList.size(); i++) {
                Article article = articleList.get(i);
                Map<String, Object> mapper = new HashMap<String, Object>();
                Blogger blogger = bloggerService.queryById(article.getBloggerId());
                mapper.put("title", article.getTitle());
                if (article.getCoverId() != null) {
                    Mediae mediae = mediaeService.queryById(article.getCoverId());
                    String real_file = DigestUtils.shaHex(blogger.getErAccount() +
                            "&" + mediae.getMdDigest());
                    String cover_path = File.separator + "api" + File.separator +
                            "resource" + File.separator + "images" + File.separator +
                            real_file + mediae.getFlagType();
                    mapper.put("cover", cover_path);
//                    System.out.println(cover_path);
                } else {
                    mapper.put("cover", "#");
                }
                // cover_path: /api/meidae/images/article-cover-default.jpg
                String base_path = session.getServletContext().getRealPath("/WEB-INF/blogger/") +
                        blogger.getErAccount() + File.separator + "article" + File.separator;
                File directory = new File(base_path);
                if (!directory.exists()) directory.mkdirs();
                String file_name = article.getFileName();
                File file = new File(base_path + file_name);
//                System.out.println(base_path + file_name);
                String segment = null;
                try {
                    byte[] bytes = FileUtils.readFileToByteArray(file);
//                    String encoded = Base64.getEncoder().encodeToString(bytes);
//                    System.out.println(bytes.length); / 2 = str
                    segment = new String(bytes, "utf-8");
//                    System.out.println(segment);
                    segment = BaseTools.delHTMLTag(segment);
//                    System.out.println(segment);
//                    System.out.println(segment.length());
                } catch (IOException e) {
//                    e.printStackTrace();
                    segment = "no content";
                }

                mapper.put("segmental", segment);
                mapper.put("post", "3 mins");
                mapper.put("blogger", blogger.getErName());
                mapper.put("views", article.getVisCount());
                mapper.put("like", article.getLikeCount());
                mapper.put("dislike", article.getDislikeCount());
                mapper.put("link", article.getLinkName());
                data.put("" + (i + 1), mapper);
            }
            responseData = new ResponseData(0, "default", data);
        } else {
            responseData = new ResponseData(-1, "failure", null);
        }
        return responseData;
    }

    @GetMapping("/web/api/article/md/content")
    public ResponseEntity<byte[]> getArticleContent(HttpSession session,
                            @RequestParam("account") String account,
                            @RequestParam("link") String link) {
        Blogger blogger = bloggerService.queryByAccount(account);
        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        String base_path = session.getServletContext().getRealPath("/WEB-INF/blogger/") +
               account + File.separator + "article" + File.separator + article.getFileName();
//        response.setContentType("application/x-download");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAcceptCharset();
        InputStream is = null;
        ResponseEntity<byte[]> responseEntity = null;
        try {
            File file = new File(base_path + ".save");
            byte[] bytes = FileUtils.readFileToByteArray(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=markdown.md");
//            headers.setContentType(MediaType.TEXT_HTML);
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
//        System.out.println(Arrays.toString(links));
        if (links == null) {
            return new ResponseData(-1, "delete failure", null);
        }

        Map<String, Object> data = new HashMap<String, Object>();
        String account = (String) session.getAttribute("account");
        Blogger blogger = bloggerService.queryByAccount(account);
        int count = 0;
        for (int i = 0; i < links.length; i++) {
            Map<String, Object> mapper = new HashMap<String, Object>();
            mapper.put("link", links[i]);
            Article article = articleService.queryByAccoutAndLink(blogger.getId(), links[i]);
//            System.out.println(account + " : " + links[i]);
            String file_path = session.getServletContext().getRealPath("/WEB-INF/blogger/") +
                    account + File.separator + "article" + File.separator + article.getFileName();
            int flag = articleService.deleteByAccountAndLink(blogger.getId(), links[i]);
//            System.out.println(file_path);
            File saved_file = new File(file_path + ".save");
            if (saved_file.exists()) {
                saved_file.delete();
                flag = 1;
            }
            File file = new File(file_path);
            if (file.exists()) {
                file.delete();
                flag = 1;
            }

            mapper.put("delete", flag);
            if (flag == 1) count += 1;
            data.put("" + (i + 1), mapper);
        }
        data.put("success", count);

        if (count == 0) {
            return new ResponseData(1, "not delete articles", null);
        } else {
            return new ResponseData(0, "delete success", data);
        }
    }

    @GetMapping("/web/api/article/editInfo")
    public ResponseData getArticlesByPageNumber(HttpSession session,
                    @RequestParam("link") String link) {
        String account = (String) session.getAttribute("account");
        Blogger blogger = bloggerService.queryByAccount(account);
        Article article = articleService.queryByAccoutAndLink(blogger.getId(), link);
        ResponseData responseData = null;
        if (article != null) {
            Map<String, Object> data = new HashMap<String, Object>();
//            Blogger blogger = bloggerService.queryById(article.getBloggerId());
            data.put("title", article.getTitle());
            data.put("account", blogger.getErAccount());
            data.put("article-link", link);
            String updateDate = BaseTools.toString(article.getUpdateDate());
            if ("-".equals(updateDate)) updateDate = BaseTools.toString(article.getPostDate());
            data.put("update-date", updateDate);
//            Mediae mediae = mediaeService.queryById(article.getCoverId());
//            String real_file = DigestUtils.shaHex(blogger.getErAccount() +
//                    "&" + mediae.getMdDigest());
//            String cover_path = File.separator + "api" + File.separator +
//                    "resource" + File.separator + "images" + File.separator +
//                    real_file + mediae.getFlagType();
//                    System.out.println(cover_path);

            responseData = new ResponseData(0, "Edit Article, Please", data);
        } else {
            responseData = new ResponseData(-1, "Article Not Exist", null);
        }
        return responseData;
    }


    @GetMapping("/web/api/article/items/page/{number}")
    public ResponseData getArticleItemsByPageNumber(
                    @PathVariable("number") Integer number) {
        int number_of_one_page = 10;
        int begin_index = (number - 1) * number_of_one_page;
        List<Article> articleList = articleService.queryAllByLimit(
                number_of_one_page, begin_index);
        ResponseData responseData = null;
        if (articleList != null) {
            Map<String, Object> data = new HashMap<String, Object>();
            for (int i = 0; i < articleList.size(); i++) {
                Article article = articleList.get(i);
                Map<String, Object> mapper = new HashMap<String, Object>();
                Blogger blogger = bloggerService.queryById(article.getBloggerId());
                mapper.put("id", begin_index + i + 1);
                mapper.put("title", article.getTitle());
                mapper.put("postDate", BaseTools.toString(article.getPostDate()));
                mapper.put("updateDate", BaseTools.toString(article.getUpdateDate()));
                mapper.put("link", article.getLinkName());
                data.put("" + (i + 1), mapper);
            }
            responseData = new ResponseData(0, "default", data);
        } else {
            responseData = new ResponseData(-1, "failure", null);
        }
        return responseData;
    }

    @PostMapping("/web/api/article/addArticle")
    public ResponseData addArticle(HttpSession session,
                       @RequestParam("title") String title,
                       @RequestParam("article") MultipartFile article,
                       @RequestParam("postDate") String postDate) {
        String account = (String) session.getAttribute("account");
        String link_name = BaseTools.randomStr(12);
        String file_name = BaseTools.randomStr(20);
        String base_path = session.getServletContext().getRealPath("/WEB-INF/") +
                "blogger" + File.separator + account + File.separator + "article";
        String file_path = base_path + File.separator + file_name;
        String backup_file_path = file_path + ".save";

        File directory = new File(base_path);
        if (!directory.exists()) {
            directory.mkdir();
        }

        ResponseData responseData = null;
        try {
            System.out.println(file_path);
            System.out.println(backup_file_path);
            File file = new File(file_path);
            File backup_file = new File(backup_file_path);
            article.transferTo(file);
            article.transferTo(backup_file);
            Blogger blogger = bloggerService.queryByAccount(account);
            Article article_ojb = new Article(0, blogger.getId(),
                    null, title, link_name, file_name, "posted",
                    BaseTools.toDate(postDate), null, 0,
                    0, 0, 0);
            articleService.insert(article_ojb);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("account", account);
            data.put("article-link", link_name);
            data.put("update-date", postDate);
            responseData = new ResponseData(0, "success", data);
        } catch (IOException e) {
            responseData = new ResponseData(-1, "failure", null);
        }
        return responseData;
    }

    @PostMapping("/web/api/article/addBackupArticle")
    public ResponseData addBackupArticle(HttpSession session,
                       @RequestParam("title") String title,
                       @RequestParam("article") MultipartFile article,
                       @RequestParam("postDate") String postDate) {
        String account = (String) session.getAttribute("account");
        String link_name = BaseTools.randomStr(12);
        String file_name = BaseTools.randomStr(20);
        String base_path = session.getServletContext().getRealPath("/WEB-INF/") +
                "blogger" + File.separator + account + File.separator + "article";
        String file_path = base_path + File.separator + file_name + ".save";

        File directory = new File(base_path);
        if (!directory.exists()) {
            directory.mkdir();
        }

        ResponseData responseData = null;
        try {
            File file = new File(file_path);
            article.transferTo(file);
            Blogger blogger = bloggerService.queryByAccount(account);
            Article article_ojb = new Article(0, blogger.getId(),
                    null, title, link_name, file_name, "saved",
                    BaseTools.toDate(postDate), null, 0,
                    0, 0, 0);
            articleService.insert(article_ojb);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("account", account);
            data.put("article-link", link_name);
            data.put("update-date", postDate);
            responseData = new ResponseData(0, "success", data);
        } catch (IOException e) {
            responseData = new ResponseData(-1, "failure", null);
        }
        return responseData;
    }

    @PostMapping("/web/api/article/updateArticle")
    public ResponseData addArticle(HttpSession session,
                       @RequestParam("article") MultipartFile article,
                       @RequestParam("title") String title,
//                       @RequestParam("account") String account,
                       @RequestParam("link") String link,
                       @RequestParam("postDate") String postDate) {
        String account = (String) session.getAttribute("account");
        Blogger blogger = bloggerService.queryByAccount(account);
        Article article_ojb = articleService.queryByAccoutAndLink(blogger.getId(), link);
        article_ojb.setTitle(title);
        article_ojb.setFlagType("updated");
        article_ojb.setUpdateDate(BaseTools.toDate(postDate));
        articleService.update(article_ojb);
        String base_path = session.getServletContext().getRealPath("/WEB-INF/blogger/") +
                File.separator + account + File.separator + "article" + File.separator;
        File directory = new File(base_path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(base_path + article_ojb.getFileName());
        File backcup_file = new File(base_path + article_ojb.getFileName() + ".save");
        try {
            article.transferTo(file);
            article.transferTo(backcup_file);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("account", account);
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
//                       @RequestParam("account") String account,
                       @RequestParam("link") String link,
                       @RequestParam("postDate") String postDate) {
        String account = (String) session.getAttribute("account");
        Blogger blogger = bloggerService.queryByAccount(account);
        Article article_ojb = articleService.queryByAccoutAndLink(blogger.getId(), link);
        article_ojb.setTitle(title);
        article_ojb.setFlagType("saved");
        article_ojb.setUpdateDate(BaseTools.toDate(postDate));
        articleService.update(article_ojb);
        String base_path = session.getServletContext().getRealPath("/WEB-INF/blogger/") +
                File.separator + account + File.separator + "article" + File.separator;
        File directory = new File(base_path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

//        File file = new File(base_path + article_ojb.getFileName());
        File backcup_file = new File(base_path + article_ojb.getFileName() + ".save");
        try {
//            article.transferTo(file);
            article.transferTo(backcup_file);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("account", account);
            data.put("article-link", link);
            data.put("update-date", postDate);
            return new ResponseData(0, "success", data);
        } catch (IOException e) {
            return new ResponseData(-1, "update failure", null);
        }
    }

}