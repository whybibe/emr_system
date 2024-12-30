package com.qingfeng.electronic.modules.back.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.modules.back.medical.dao.DischargeRegistrationMapper;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DischargeRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.ro.DischargeRegistrationRo;
import com.qingfeng.electronic.modules.back.medical.domain.vo.DischargeRegistrationVo;
import com.qingfeng.electronic.modules.back.medical.service.DischargeRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 出院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Service
public class DischargeRegistrationServiceImpl extends ServiceImpl<DischargeRegistrationMapper, DischargeRegistration> implements DischargeRegistrationService {

    private DischargeRegistrationMapper dischargeRegistrationMapper;

    @Autowired
    public DischargeRegistrationServiceImpl(DischargeRegistrationMapper dischargeRegistrationMapper) {
        this.dischargeRegistrationMapper = dischargeRegistrationMapper;
    }

    /**
     * 查询出院登记分页列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public DischargeRegistrationVo selectDischargeRegistrationPage(Long pageNo,
                                                                   Long pageSize,
                                                                   String userName,
                                                                   Long id,
                                                                   String department,
                                                                   String patient,
                                                                   String wardNumber,
                                                                   String bedNumber,
                                                                   Date dischargeDate) {
        // 先计算要分页的页码和大小
        // 先根据条件查询总记录数
        Long total = dischargeRegistrationMapper.selectDischargeRegistrationCount(
                id,
                userName,
                department,
                patient,
                wardNumber,
                bedNumber,
                dischargeDate
        );
        if (total == 0) {
            return DischargeRegistrationVo.builder()
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .dischargeRegistrationRoList(Collections.emptyList())
                    .total(total)
                    .build();
        }

        List<DischargeRegistrationRo> dischargeRegistrationRoList = dischargeRegistrationMapper.selectDischargeRegistrationPage(
                id,
                userName,
                department,
                patient,
                wardNumber,
                bedNumber,
                dischargeDate,
                (pageNo - 1) * pageSize,
                pageSize
        );

        return DischargeRegistrationVo.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .dischargeRegistrationRoList(dischargeRegistrationRoList)
                .total(total)
                .build();
    }
}