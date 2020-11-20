package com.bingan.myblog.web;

import com.bingan.myblog.service.BlogService;
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

import com.bingan.myblog.entity.Type;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    TypesService typesService;
    @Autowired
    BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
            Pageable pageable, @PathVariable Long id, Model model){
        List<Type> types = typesService.listTypeTop(10000);
        if(id==-1){
            id = types.get(0).getId();
        }
        model.addAttribute("types", types);
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("page", blogService.listBlogs(pageable,blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
