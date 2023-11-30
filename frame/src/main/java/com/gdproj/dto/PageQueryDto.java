package com.gdproj.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryDto {
    @NotNull(message = "分页参数num为空")
    private Integer pageNum;
    @NotNull(message = "分页参数size为空")
    private Integer pageSize;
    private Integer departmentId;
    private Integer type;
    private String title = "";
    private String time;
    private String sort = "+id";
    private Integer status;
    public PageQueryDto(Integer pageNum , Integer pageSize ,  Integer departmentId , Integer type ,String title ,String time, String sort){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.departmentId =departmentId;
        this.time =time;
        this.type =type;
        this.title = title;
        this.sort = sort;
    }
    public PageQueryDto(Integer pageNum , Integer pageSize){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
