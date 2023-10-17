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
public class selectVo {

    private Integer Id;
    private String Name;
    private List<selectVo> children;
    private Integer parentId;
}
