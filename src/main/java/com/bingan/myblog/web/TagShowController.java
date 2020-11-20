package com.bingan.myblog.web;

import com.bingan.myblog.entity.Tag;
import com.bingan.myblog.service.BlogService;
import com.bingan.myblog.service.TagService;
import com.bingan.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(10000);
        if(id==-1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlogs(pageable, id));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
