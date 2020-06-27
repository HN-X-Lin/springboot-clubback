package com.lin.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.pojo.Tag;
import com.lin.pojo.Type;
import com.lin.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @ClassName TagController
 * @Description
 * @Author xiaolin
 * @Date 2020/6/26 11:59
 * @Version V1.0
 */

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private  TagService tagService;
 /**
  * @title TagController
  * @description
  * @return
  * @author lizhuo
  * @updateTime 2020/6/26 17:19
  */
    @GetMapping("/tags")
    public String tags(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum,5);
        List<Tag> allTag = tagService.getAllTag();

        PageInfo<Tag> pageInfo = new PageInfo<>(allTag);
        model.addAttribute("pageInfo",pageInfo );
        return "admin/tags";
    }
    /**
     * @title DeleteTag
     * @description 删除指定标签
     * @param id
     * @param attributes
     * @return
     * @author lizhuo
     * @updateTime 2020/6/27 15:40
     */
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg","删除成功");
        return "redirect:/admin/tags";
    }
     /**
      * @title toAddTags
      * @description  进入新增页面
      * @param model
      * @return
      * @author lizhuo
      * @updateTime 2020/6/27 16:03
      */
     @GetMapping("/tags/input")
    public String toAddTag(Model model){
         model.addAttribute("tag",new Tag());
         return "admin/tags-input";
     }

     /**
      * @title toEditTag
      * @description  跳转到修改界面
      * @param id
      * @param model
      * @return java.lang.String
      * @author lizhuo
      * @updateTime 2020/6/27 16:40
      */
     @GetMapping("/tags/{id}/input")
     public String toEditTag(@PathVariable Long id,Model model){
         model.addAttribute("tag",tagService.getTag(id));
         return "admin/tags-input";
     }
     /**
      * @title addTags
      * @description  添加标签
      * @param tag
      * @param attributes
      * @return java.lang.String
      * @author lizhuo
      * @updateTime 2020/6/27 16:31
      */
     @PostMapping("/tags")
    public String addTag(Tag tag,RedirectAttributes attributes){
         Tag t = tagService.getTagByName(tag.getName());
         if(t != null){
             attributes.addFlashAttribute("msg", "不能添加重复的标签");
             return "redirect:/admin/tags/input";
         }else {
             attributes.addFlashAttribute("msg", "添加成功");
         }
         tagService.saveTag(tag);
         return "redirect:/admin/tags";
     }

     /**
      * @title editTag
      * @description  修改指定标签
      * @param id
      * @param tag
      * @param attributes
      * @return java.lang.String
      * @author lizhuo
      * @updateTime 2020/6/27 16:49
      */
     @PostMapping("/tags/{id}")
    public String editTag(@PathVariable Long id, Tag tag, RedirectAttributes attributes){
         Tag t = tagService.getTagByName(tag.getName());
         if(t != null){
             attributes.addFlashAttribute("msg", "不能添加重复的标签");
             return "redirect:/admin/tags/input";
         }else {
             attributes.addFlashAttribute("msg", "修改成功");
         }
         tagService.updateTag(tag);
         return "redirect:/admin/tags";
     }
}
