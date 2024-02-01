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
public class TicketVo {

    private Integer ticketId;

    private String ticketName;

    private String ticketCompany;

    private List<FileVo> ticketUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    private Integer createdUser;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ticketTime;

    private String ticketRemark;

    private Integer categoryId;


    private Integer orderId;
}
