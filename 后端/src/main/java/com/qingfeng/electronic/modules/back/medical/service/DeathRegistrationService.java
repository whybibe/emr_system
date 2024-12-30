package com.qingfeng.electronic.modules.back.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DeathRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.vo.DeathRegistrationVo;

import java.util.Date;

/**
 * 死亡登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
public interface DeathRegistrationService extends IService<DeathRegistration> {

    /**
     * 分页查询死亡登记记录
     * @param pageNo
     * @param pageSize
     * @param userName
     * @param department
     * @param patient
     * @param wardNumber
     * @param bedNumber
     * @param deathDate
     * @return
     */
    DeathRegistrationVo selectDeathRegistrationPage(Long pageNo,
                                                    Long pageSize,
                                                    String userName,
                                                    String department,
                                                    String patient,
                                                    String wardNumber,
                                                    String bedNumber,
                                                    Date deathDate,
                                                    Long id);
}

