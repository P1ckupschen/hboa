package com.gdproj.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignDto {
    private Integer pageNum;
    private Integer pageSize;
    private Integer departmentId;
    private String Keyword;
    @JsonProperty("Interval")
    private List<Date> Interval;
}
