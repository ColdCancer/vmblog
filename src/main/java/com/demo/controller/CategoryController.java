package com.demo.controller;

import com.demo.entity.Blogger;
import com.demo.entity.Category;
import com.demo.service.BloggerService;
import com.demo.service.CategoryLinkService;
import com.demo.service.CategoryService;
import com.demo.utils.ResponseData;
import com.demo.utils.ResponseState;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Category)表控制层
 *
 * @author vmice
 * @since 2022-04-04 18:59:53
 */
@RestController
public class CategoryController {
    @Resource
    private BloggerService bloggerService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private CategoryLinkService categoryLinkService;

    // @GetMapping("selectOne")
    // public Category selectOne(Integer id) {
    //     return this.categoryService.queryById(id);
    // }

    @GetMapping("/web/api/category/list")
    public ResponseData getCategorList(HttpSession session) {
        String account = (String) session.getAttribute("account");
        Blogger blogger = bloggerService.queryByAccount(account);
        List<Category> categories = categoryService.queryByBloggerId(blogger.getId());
        if (categories.size() == 0) return new ResponseData(ResponseState.EMPTY, null);
        Map<String, Object> data = new HashMap<String, Object>();
        Map<Integer, Integer> id_map = new HashMap<Integer, Integer>();
        for (int i = 0; i < categories.size(); i++) {
            id_map.put(categories.get(i).getId(), i);
        }
        // Map<String, int> category_count = new HashMap<Stirng, int>();
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            // category_count.put(category.getFlagType,
            //                    categoryLinkService.queryCountById(category.getId));
            // if (category.getParentId() == null) continue;
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("count", category.getParentId() == null ? 0 :
                    categoryLinkService.queryCountById(category.getId()));
            item.put("name", category.getTypeName());
            item.put("parentId", id_map.get(category.getParentId()));
            data.put("" + i, item);
        }
        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @PostMapping("/web/api/category/addClassify")
    public ResponseData addClaasify(HttpSession session,
                        @RequestParam("classify") String classify) {
        String account = (String) session.getAttribute("account");
        Blogger blogger = bloggerService.queryByAccount(account);
        Category category = categoryService.queryByIdAndType(blogger.getId(), classify);
        if (category != null) return new ResponseData(ResponseState.EMPTY, null);
        category = new Category(null, blogger.getId(), classify, null);
        category = categoryService.insert(category);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("add", category.getId() != null);
        return new ResponseData(ResponseState.SUCCESS, data);
    }

}