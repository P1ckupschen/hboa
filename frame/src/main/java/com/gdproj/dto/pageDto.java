package com.gdproj.dto;


import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class pageDto {
    private Integer pageNum;

    private Integer pageSize;

    private Integer type;

    private String title;

    private String time;

    private String sort;
}
