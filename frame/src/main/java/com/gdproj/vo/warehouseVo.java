package com.gdproj.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class warehouseVo {

    private Integer warehouseId;

    private String warehouseTitle;

    private List<warehouseSelectVo> warehouseContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private Integer categoryId;
    private String warehouseFeedback;

    private String warehouseDescription;

    private Integer warehouseStatus;

    private List<fileVo> warehouseAttachments;

    private Integer typeId;

    private String Username;

    private Integer userId;

    private String Category;

    private Integer contractId;

    private Integer projectId;
}
