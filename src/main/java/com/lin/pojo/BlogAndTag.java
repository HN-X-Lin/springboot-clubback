package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName BlogAndTag
 * @Description
 * @Author xiaolin
 * @Date 2020/6/30 13:00
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogAndTag {
    private Long tagId;

    private Long blogId;
}
