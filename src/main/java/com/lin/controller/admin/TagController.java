package com.lin.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.pojo.Blog;
import com.lin.pojo.Tag;
import com.lin.pojo.Type;
import com.lin.service.BlogService;
import com.lin.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    @Autowired
    private BlogService blogService;
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

        Tag tag=tagService.getTag(id);
        tag.setBlogs(blogService.getByTagId(tag.getId(),tag.getName()));//获取所有含有该标签的blog
        blogService.deleteBlogId_TagId(tag.getId());//删除t_blog_tags中的有关该tag的数据
        //System.out.println(tag);

        for(Blog blog: tag.getBlogs()){//剔除含有该标签的blog中的该标签
            String[] tagIds=blog.getTagIds().split(",");//分割字符
            //System.out.println(tagIds);
            int len=tagIds.length;
            int flag=1;
            List<Tag> tags = new ArrayList<>();
            for(int i=0;i<len;i++){
                Long tagId=Long.valueOf(Integer.parseInt(tagIds[i]));
                //System.out.println(tagId);
                if(tagId!=id){
                    tags.add(tagService.getTag(tagId));
                }
                if(tagId==0){
                    flag=0;
                }
            }
            if(flag==1&&len==1){//当要删除的标签为该blog的最后一个标签
                tags.add(tagService.getTag(Long.valueOf(0)));
                blogService.saveBlogId_TagId(blog.getId(),Long.valueOf(0));
            }

            blog.setTags(tags);
            blog.init();//通过设置blog的tags来设置blog的tag_ids

            blogService.updateBlogTag(blog);//更新除去删除的标签之后的blog的tag_ids
        }
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
