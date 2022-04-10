package com.demo.controller;

import com.demo.dao.BloggerDao;
import com.demo.entity.Blogger;
import com.demo.service.BloggerService;
import com.demo.utils.BaseTools;
import com.demo.utils.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
}