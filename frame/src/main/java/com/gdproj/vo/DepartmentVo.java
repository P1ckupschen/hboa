package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DepartmentVo {

    private Integer departmentId;

    private String departmentName;

    private Integer departmentHead;

    private String departmentFax;

    private String departmentPhone;

    private Integer parentId;

    private List<DepartmentVo> children;
}
