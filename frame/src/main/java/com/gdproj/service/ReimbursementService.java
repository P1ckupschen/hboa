package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Reimbursement;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.ReimbursementVo;

/**
* @author Administrator
* @description 针对表【sys_reimbursement】的数据库操作Service
* @createDate 2023-10-19 14:22:09
*/
public interface ReimbursementService extends IService<Reimbursement> {

    boolean insertReimbursement(Reimbursement reimbursement);

    ResponseResult updateReimbursement(ReimbursementVo vo);

    ResponseResult deleteReimbursement(Integer id);

    ResponseResult getReimbursementList(PageQueryDto queryVo);
}
