package com.demo.controller;

import com.demo.dao.BloggerDao;
import com.demo.entity.Blogger;
import com.demo.service.BloggerService;
import com.demo.utils.BaseTools;
import com.demo.utils.ResponseData;
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

    @SneakyThrows
    @PostMapping("web/passport/signup")
    public ResponseData signup(@Param("account")String account,
                               @Param("password")String password) {
        Blogger blogger = new Blogger();
        blogger.setErName("blogger");
        blogger.setErAccount(account);
        blogger.setSaSalt(BaseTools.UUID());
        String real_password = password + "&" + blogger.getSaSalt();
        blogger.setErPassword(DigestUtils.shaHex(real_password));

        int flag = bloggerService.insert(blogger);
        String message = "Sign Up " + (flag == 1 ? "Success." : "Failure.");

        ResponseData responseData = new ResponseData(flag, message, null);
        return responseData;
    }

    @SneakyThrows
    @GetMapping("api/passport/status")
    public ResponseData loginStatus(HttpSession session) {
        Map<String, Object> data = new HashMap<String, Object>();
        ResponseData responseData = null;
//        System.out.println(session.getAttribute("account"));
        if (session.getAttribute("account") != null) {
            data.put("account", session.getAttribute("account"));
            responseData = new ResponseData(0, "Sign In Success.", data);
        } else {
            responseData = new ResponseData(-1, "Sign In Failure.", null);
        }
        return responseData;
    }

    @SneakyThrows
    @PostMapping("web/passport/signin")
    public ResponseData signin(HttpServletRequest request,
                               HttpServletResponse response,
                               @Param("account")String account,
                               @Param("password")String password,
                               @Param("remember")boolean remember) {

        ResponseData responseData = null;
        if(bloggerService.checkAccountValid(account, password, remember)) {
            responseData = new ResponseData(0, "Sign In Success.", null);
            HttpSession session = request.getSession();
            session.setAttribute("account", account);
        } else {
            responseData = new ResponseData(-1, "Sign In Failure.", null);
        }

        return responseData;
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
}