package com.bingan.myblog.web.admin;

import com.bingan.myblog.entity.Tag;
import com.bingan.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model){
        Page<Tag> page = tagService.listTag(pageable);
        model.addAttribute("page", page);
        return "/admin/tags";
    }

    @PostMapping("/tags")
    public String addTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null){
            result.rejectValue("name", "nameError", "不能添加重复的标签");
        }
        if (result.hasErrors()){
            return "/admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "修改失败");
        } else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null){
            result.rejectValue("name", "nameError", "不能添加重复的标签");
        }
        if (result.hasErrors()){
            return "/admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "添加失败");
        } else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "/admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id, Model model){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag", tag);
        return  "/admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String removeTag(@PathVariable Long id){
        tagService.removeTag(id);
        return "redirect:/admin/tags";
    }
}
