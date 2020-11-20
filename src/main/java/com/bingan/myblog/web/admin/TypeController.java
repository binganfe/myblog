package com.bingan.myblog.web.admin;

import com.bingan.myblog.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.bingan.myblog.entity.Type;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypesService typesService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){

        model.addAttribute("page", typesService.listType(pageable));
        return "/admin/types";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typesService.getTypeByName(type.getName());
        if (type1!=null){
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "/admin/types-input";
        }

        Type t = typesService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "添加失败");
        } else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/types"; //重定向会被Controller再次捕获，重新查询
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable  Long id, RedirectAttributes attributes){
        Type type1 = typesService.getTypeByName(type.getName());
        if (type1!=null){
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "/admin/types-input";
        }

        Type t = typesService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types"; //重定向会被Controller再次捕获，重新查询
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "/admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typesService.getType(id));
        return "/admin/types-input";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typesService.removeType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

}
