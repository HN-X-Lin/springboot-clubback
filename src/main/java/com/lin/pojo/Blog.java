package com.lin.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Blog
 * @Description 博客
 * @Author xiaolin
 * @Date 2020/6/21 12:24
 * @Version V1.0
 */
@Data
public class Blog {
    private Long id;//博客id号
    private String title;//博客标题
    private String content;//博客内容
    private String firstPicture;
    private String flag;//是否原创
    private Integer views;//浏览阅读人数
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;//创作时间
    private Date updateTime;//更新时间

    //这个属性用来在mybatis中进行连接查询的
    private Long typeId;
    private Long userId;

    //获取多个标签的id
    private String tagIds;
    private String description;

    private Type type;

    private User user;

    private List<Tag> tags = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    //将tags集合转换为tagIds字符串形式：“1,2,3”,用于编辑博客时显示博客的tag
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag: tags){
                if(flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }
}
