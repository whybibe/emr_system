package com.qingfeng.electronic.modules.back.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.modules.back.medical.dao.DeathRegistrationMapper;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DeathRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.ro.DeathRegistrationRo;
import com.qingfeng.electronic.modules.back.medical.domain.vo.DeathRegistrationVo;
import com.qingfeng.electronic.modules.back.medical.service.DeathRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 死亡登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Service
public class DeathRegistrationServiceImpl extends ServiceImpl<DeathRegistrationMapper, DeathRegistration> implements DeathRegistrationService {

    private DeathRegistrationMapper deathRegistrationMapper;

    @Autowired
    public DeathRegistrationServiceImpl(DeathRegistrationMapper deathRegistrationMapper) {
        this.deathRegistrationMapper = deathRegistrationMapper;
    }

    /**
     * 分页查询所有死亡登记记录
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
    @Override
    public DeathRegistrationVo selectDeathRegistrationPage(Long pageNo,
                                                           Long pageSize,
                                                           String userName,
                                                           String department,
                                                           String patient,
                                                           String wardNumber,
                                                           String bedNumber,
                                                           Date deathDate,
                                                           Long id) {
        // 先计算要分页的页码和大小
        // 先根据条件查询总记录数
        Long total = deathRegistrationMapper.selectDeathRegistrationCount(
                userName,
                department,
                patient,
                wardNumber,
                bedNumber,
                deathDate,
                id
        );
        if (total == 0) {
            return DeathRegistrationVo.builder()
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .deathRegistrationRoList(Collections.emptyList())
                    .total(total)
                    .build();
        }

        List<DeathRegistrationRo> deathRegistrationRoList = deathRegistrationMapper.selectDeathRegistrationPage(
                userName,
                department,
                patient,
                wardNumber,
                bedNumber,
                deathDate,
                id,
                (pageNo - 1) * pageSize,
                pageSize
        );

        return DeathRegistrationVo.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .deathRegistrationRoList(deathRegistrationRoList)
                .total(total)
                .build();
    }
}