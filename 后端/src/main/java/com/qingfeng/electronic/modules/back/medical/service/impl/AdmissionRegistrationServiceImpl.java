package com.qingfeng.electronic.modules.back.medical.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.modules.back.medical.dao.AdmissionRegistrationMapper;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistration;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 入院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Service
public class AdmissionRegistrationServiceImpl extends ServiceImpl<AdmissionRegistrationMapper, AdmissionRegistration> implements AdmissionRegistrationService {


    /**
     * 查询入院登记列表
     *
     * @param pageNo   页码
     * @param pageSize 页面大小
     * @return {@link List}<{@link AdmissionRegistration}>
     */
    @Override
    public Page<AdmissionRegistration> selectAdmissionRegistrationList(Long pageNo, Long pageSize, String userName, String identificationNumber) {
        return baseMapper.selectPage(
                new Page<>(pageNo, pageSize),
                new LambdaQueryWrapper<AdmissionRegistration>()
                        .eq(StrUtil.isNotBlank(userName), AdmissionRegistration::getUserName, userName)
                        .eq(StrUtil.isNotBlank(identificationNumber), AdmissionRegistration::getIdentificationNumber, identificationNumber)
                        .orderByDesc(BaseEntity::getCreateTime)
        );
    }
}