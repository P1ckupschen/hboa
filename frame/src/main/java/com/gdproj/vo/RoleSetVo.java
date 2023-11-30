package com.gdproj.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleSetVo {

    private Integer DeployeeId;
    private List<Integer> list;
}
