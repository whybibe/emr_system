package com.qingfeng.electronic.modules.back.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DischargeRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.vo.DischargeRegistrationVo;

import java.util.Date;

/**
 * 出院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
public interface DischargeRegistrationService extends IService<DischargeRegistration> {

    /**
     * 查询出院登记表分页列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    DischargeRegistrationVo selectDischargeRegistrationPage(
            Long pageNo,
            Long pageSize,
            String userName,
            Long id,
            String department,
            String patient,
            String wardNumber,
            String bedNumber,
            Date dischargeDate);
}

