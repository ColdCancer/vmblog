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
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        // Blogger blogger = bloggerService.queryByAccount(account);
        List<Category> categories = categoryService.queryByBloggerId(blogger.getId());
        if (categories.size() == 0) return new ResponseData(ResponseState.EMPTY, null);
        Map<String, Object> data = new HashMap<String, Object>();
        Map<Integer, Integer> id_map = new HashMap<Integer, Integer>();
        for (int i = 0; i < categories.size(); i++) {
            id_map.put(categories.get(i).getId(), i);
        }
        // Map<String, int> category_count = new HashMap<Stirng, int>();
        // int son_count = 0;
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            // son_count += (category.getParentId() == null ? 0 : 1);
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
        // String account = (String) session.getAttribute("account");
        // Blogger blogger = bloggerService.queryByAccount(account);
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        Category category = categoryService.queryByIdAndType(blogger.getId(), classify);
        if (category != null) return new ResponseData(ResponseState.EMPTY, null);
        category = new Category(null, blogger.getId(), classify, null);
        category = categoryService.insert(category);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("add", category.getId() != null);
        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @PostMapping("/web/api/category/renameClassify")
    public ResponseData renameClaasify(HttpSession session,
                                       @RequestParam("srcClassify") String srcClassify,
                                       @RequestParam("desClassify") String desClassify) {
        // String account = (String) session.getAttribute("account");
        // Blogger blogger = bloggerService.queryByAccount(account);
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        Category category = categoryService.queryByIdAndType(blogger.getId(), desClassify);
        if (category != null) return new ResponseData(ResponseState.EMPTY, null);
        category = categoryService.queryByIdAndType(blogger.getId(), srcClassify);
        if (category == null) return new ResponseData(ResponseState.EMPTY, null);
        // category = new Category(null, blogger.getId(), classify, null, null);
        int flag = categoryService.updateTypeNameById(category.getId(), desClassify);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rename", flag);
        return new ResponseData(ResponseState.SUCCESS, data);
    }


    @PostMapping("/web/api/category/modifyParent")
    public ResponseData modifyParent(HttpSession session,
                         @RequestParam("sonType") String sonType,
                         @RequestParam("parentType") String parentType) {
        // String account = (String) session.getAttribute("account");
        // Blogger blogger = bloggerService.queryByAccount(account);
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        Category sonCategory = categoryService.queryByBloggerIdAndTypeName(blogger.getId(), sonType);
        if (sonCategory == null) return new ResponseData(ResponseState.EMPTY, null);
        Category parentCategory = categoryService.queryByBloggerIdAndTypeName(blogger.getId(), parentType);
        if (parentCategory == null) return new ResponseData(ResponseState.EMPTY, null);
        int sonCount = categoryService.querySonCountByIds(blogger.getId(), sonCategory.getId());
        if (sonCount != 0) return new ResponseData(ResponseState.EMPTY, null);
        int parentCount = parentCategory.getParentId() == null ? 0 : 1;
        if (parentCount != 0) return new ResponseData(ResponseState.EMPTY, null);
        int flag = categoryService.updateParentByIds(sonCategory.getId(), parentCategory.getId());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("update", flag);
        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @PostMapping("/web/api/category/isolateParent")
    public ResponseData isolateParent(HttpSession session,
                          @RequestParam("classify") String classify) {
        // String account = (String) session.getAttribute("account");
        // Blogger blogger = bloggerService.queryByAccount(account);
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        Category category = categoryService.queryByBloggerIdAndTypeName(blogger.getId(), classify);
        if (category == null) return new ResponseData(ResponseState.EMPTY, null);
        if (category.getParentId() == null) {
            return new ResponseData(ResponseState.EMPTY, null);
        }
        int flag = categoryService.updateParentByIds(category.getId(), null);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("isolate", flag);
        return new ResponseData(ResponseState.SUCCESS, data);
    }

    @PostMapping("/web/api/category/removeClassify")
    public ResponseData removeClaasify(HttpSession session,
                       @RequestParam("classify") String classify) {
        // String account = (String) session.getAttribute("account");
        // Blogger blogger = bloggerService.queryByAccount(account);
        Blogger blogger = (Blogger) session.getAttribute("blogger");
        Category category = categoryService.queryByIdAndType(blogger.getId(), classify);
        if (category == null) return new ResponseData(ResponseState.EMPTY, null);
        List<Category> categories = categoryService.queryByIds(blogger.getId(), category.getId());
        // category = new Category(null, blogger.getId(), classify, null, null);
        int isolate_flag = 0;
        if (categories.size() != 0) {
            for (Category item : categories) {
                isolate_flag += categoryService.updateParentByIds(item.getId(), null);
            }
        }
        int flag = categoryService.deleteById(category.getId());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("isolate-count", isolate_flag);
        data.put("delete", flag);
        return new ResponseData(ResponseState.SUCCESS, data);
    }

    // @GetMapping("/web/api/category/list")
    // public ResponseData getCategoryList(HttpSession session) {
    //     String account = (String) session.getAttribute("account");
    //     Blogger blogger = bloggerService.queryByAccount(account);
    //     List<Category> categories = categoryService.queryByBloggerId(blogger.getId());
    //     if (categories.size() == 0) return new ResponseData(ResponseState.EMPTY, null);
    //     Map<String, Object> data = new HashMap<String, Object>();
    //     for (int i = 0; i < categories.size(); i++) {
    //         data.put("" + i, categories.get(i).getTypeName());
    //     }
    //     return new ResponseData(ResponseState.SUCCESS, data);
    // }

}