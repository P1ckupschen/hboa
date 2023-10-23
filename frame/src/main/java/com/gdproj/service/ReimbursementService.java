package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.entity.Reimbursement;

/**
* @author Administrator
* @description 针对表【sys_reimbursement】的数据库操作Service
* @createDate 2023-10-19 14:22:09
*/
public interface ReimbursementService extends IService<Reimbursement> {

    boolean insertReimbursement(Reimbursement reimbursement);

}
