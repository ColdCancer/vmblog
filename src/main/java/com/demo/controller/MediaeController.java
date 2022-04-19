package com.demo.controller;

import com.demo.entity.Blogger;
import com.demo.entity.Mediae;
import com.demo.service.BloggerService;
import com.demo.service.MediaeService;
import com.demo.utils.BaseTools;
import com.demo.utils.ResponseData;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * (Mediae)表控制层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@RestController
public class MediaeController {
    @Resource
    private MediaeService mediaeService;
    @Resource
    private BloggerService bloggerService;

    // @GetMapping("selectOne")
    // public Mediae selectOne(Integer id) {
    //     return this.mediaeService.queryById(id);
    // }

    @PostMapping("/api/mediae/images/upload")
    public ResponseData uploadImage(MultipartFile image, HttpSession session) {
//        return this.mediaeService.queryById(id);
//        System.out.println(image);
        String account = (String) session.getAttribute("account");
//        System.out.println(account);
        String mediae_name = image.getOriginalFilename();
        int last_index = mediae_name.lastIndexOf(".");
        String file_type = mediae_name.substring(last_index);
        mediae_name = mediae_name.substring(0, last_index);
        String mediae_digest = BaseTools.randomStr(18);
        String real_name = BaseTools.digest(account, mediae_digest);
        String base_path = session.getServletContext().getRealPath("/WEB-INF/mediae/images/");

        File file = new File(base_path + real_name + file_type);
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            image.transferTo(file);
            data.put("url", "/api/resource/images/" + real_name + file_type);
            Blogger blogger = bloggerService.queryByAccount(account);
            Mediae mediae = new Mediae(null, blogger.getId(), mediae_name,
                    mediae_digest, file_type, null);
            mediaeService.insert(mediae);
            return new ResponseData(0, "upload image success", data);
        } catch (IOException e) {
            data.put("url", "null");
            return new ResponseData(-1, "upload image error", data);
//            e.printStackTrace();
        }
    }

}