package com.bingan.myblog.web;

import com.bingan.myblog.exception.NotFoundException;
import com.bingan.myblog.service.BlogService;
import com.bingan.myblog.service.TagService;
import com.bingan.myblog.service.TypesService;
import com.bingan.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypesService typesService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page", blogService.listBlogs(pageable));
        model.addAttribute("types", typesService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogsTop(8));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                 Pageable pageable, @RequestParam String query, Model model){
        model.addAttribute("page", blogService.listBlogs(pageable, "%"+query+"%"));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newblogs", blogService.listRecommendBlogsTop(3));
        return "_fragments :: newblogList";
    }
}
