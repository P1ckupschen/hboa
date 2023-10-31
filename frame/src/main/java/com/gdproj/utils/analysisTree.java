package com.gdproj.utils;


import com.gdproj.vo.CategoryVo;

import java.util.List;
import java.util.stream.Collectors;

public class analysisTree {

    public static List<CategoryVo> builderTree(List<CategoryVo> list, Integer parentId) {
        List<CategoryVo> Tree = list.stream()
                .filter(item -> item.getParentId().equals(parentId))
                //set方法报错是因为set没有返回值而map需要返回值
                //1.类上添加链式编程注解@Accessors(chain = true)
                //2.手动返回
                .map(item -> item.setChildren(getChildren(item, list)))
                .collect(Collectors.toList());
        return Tree;
    }

    /**
     * 获取存入参数的
     * @param
     * @param
     * @return
     */
    public static List<CategoryVo> getChildren(CategoryVo vo, List<CategoryVo> list) {
        List<CategoryVo> childrenList = list.stream()
                .filter(m -> m.getParentId().equals(vo.getCategoryId()))
                .map(m->m.setChildren(getChildren(m,list)))
                .collect(Collectors.toList());
        return childrenList;
    }

}
