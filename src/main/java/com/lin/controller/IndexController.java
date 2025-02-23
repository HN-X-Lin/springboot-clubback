package com.lin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.pojo.*;
import com.lin.service.BlogService;
import com.lin.service.LikeService;
import com.lin.service.TagService;
import com.lin.service.TypeService;
import com.lin.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName IndexContreller
 * @Description
 * @Author xiaolin
 * @Date 2020/6/21 12:52
 * @Version V1.0
 */
@Controller
public class IndexController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LikeService likeService;

    @GetMapping("/")
    public String index(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pagenum, Model model){

        PageHelper.startPage(pagenum, 8);

        List<Blog> allBlog = blogService.getIndexBlog();
        List<Type> allType = typeService.getBlogType();  //获取博客的类型(联表查询)

        List<Tag> allTag = tagService.getAllTag();
        for(Tag tag : allTag){
            tag.setBlogs(blogService.getByTagId(tag.getId(),tag.getName()));//查询有该标签的所有blog
        }
        List<Blog> recommendBlog =blogService.getAllRecommendBlog();  //获取推荐博客

        for(Blog blog: allBlog) {
            String key = "blog"+blog.getId();
            if(redisUtil.get(key) != null){//获取redis中的views，mysql中的可能没更新
                blog.setViews((Integer) redisUtil.get(key));
            }else{
                redisUtil.set(key,blog.getViews());
            }
        }

        //得到分页结果对象
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendBlogs", recommendBlog);

        return "user/index";
    }



    @GetMapping("/user/blog/{id}")
    public String blogs(@PathVariable Long id,Model model){

        //System.out.println("11111111111111");
        Blog blog = blogService.getDetailedBlog(id);
        String key="blog"+blog.getId();//key为获取redis中数据key

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();


        String keyLikes = "blogLikes"+blog.getId();
        if (user == null){//进入blog详细页面时点赞按钮的显示状态
            blog.setLikeFlag(false);
        }else{
            String keyFlag = "blog" + blog.getId() + "user" + user.getId();
            Like like = likeService.getLike(user.getId(),blog.getId());
            if(like != null && redisUtil.get(keyFlag) == null){//当mysql中该用户已存入数据，redis未存入数据时
                redisUtil.set(keyFlag,true);
                blog.setLikeFlag(true);
            }else if(redisUtil.get(keyFlag) != null){//redis已存入数据
                blog.setLikeFlag(true);
            }else {//该用户没有给blog点赞
                blog.setLikeFlag(false);
            }
        }


        if(redisUtil.get(keyLikes)==null){//blog的点赞数
            redisUtil.set(keyLikes,blog.getBlog_like());//redis中不存在时插入
        }else {
            blog.setBlog_like((Integer) redisUtil.get(keyLikes));//存在取用
        }




        if(redisUtil.get(key)==null){//判断redis中是否有该blog的views
            redisUtil.set(key,blog.getViews());//没有时添加该key-value值
            redisUtil.incr(key,1);//浏览数+1
        }else{
//            System.out.println(redisUtil.get(key));
            redisUtil.incr(key,1);
//            System.out.println(redisUtil.get(key));
            Integer blogViews= (Integer) redisUtil.get(key);//获取添加之后的redis中浏览数，因为mysql中的浏览数可能是没有更新过的
            if(blogViews % 5 == 0 && blogViews != 0){//每增加50浏览数更新一遍mysql中的views
                blogService.updateViews(blog.getId(),Long.valueOf(blogViews));
            }
            blog.setViews(blogViews-1);//因为是先添加的所以要-1
        }

//        System.out.println(redisUtil.get(key));
//        redisUtil.incr(key,1);
//        System.out.println(redisUtil.get(key));

        model.addAttribute("blog", blog);

        return "user/blog";
    }

    @PostMapping("/user/search")
    public String search(@RequestParam String info, Model model){


        List<Blog> searchBlog = blogService.getSearchBlog(info);
        PageInfo pageInfo = new PageInfo(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("info", info);
        return "user/search";
    }

    
    /**
     * @title IndexController
     * @description  点赞按钮
     * @param uid
     * @param bid
     * @return java.lang.String
     * @author lizhuo
     * @updateTime 2020/8/4 10:41
     */
    @RequestMapping("user/blog/{id}/like")
    @ResponseBody//将返回的数据转换为json格式
    public String changLike(Long uid, Long bid){
        String key = "blog"+bid+"user"+uid;
        String keyLikes = "blogLikes"+bid;
        String flag = "";
        if(redisUtil.get(key)!=null){//取消点赞
            redisUtil.del(key);
            redisUtil.decr(keyLikes,1);
            flag = "no";
        }else{//点赞
            redisUtil.set(key,true);
            redisUtil.incr(keyLikes,1);
            flag = "ok";
        }
        return flag;
    }
}
