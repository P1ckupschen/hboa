package com.gdproj.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class flowConfigVo {

    private Integer typeId;

    private String typeName;

    private List<Integer> approvalFlow;
}
