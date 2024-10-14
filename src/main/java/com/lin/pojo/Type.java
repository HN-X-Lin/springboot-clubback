package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Type
 * @Description 类型
 * @Author xiaolin
 * @Date 2020/6/22 19:42
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    private Long id;//类型id号
    private String name;//类型名称
    private List<Blog> blogs = new ArrayList<>();

}
