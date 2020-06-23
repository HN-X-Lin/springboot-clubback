package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Tag
 * @Description 标签
 * @Author xiaolin
 * @Date 2020/6/22 19:34
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    private Long id;
    private String name;
    private List<Blog> blogs = new ArrayList<>();

}
