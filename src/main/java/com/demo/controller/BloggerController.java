package com.demo.controller;

import com.demo.dao.BloggerDao;
import com.demo.entity.Blogger;
import com.demo.service.BloggerService;
import com.demo.utils.BaseTools;
import com.demo.utils.ResponseData;
import com.demo.utils.ResponseState;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.net.HttpResponse;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
}