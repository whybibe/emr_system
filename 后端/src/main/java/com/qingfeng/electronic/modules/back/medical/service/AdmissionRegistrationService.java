package com.qingfeng.electronic.modules.back.medical.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistration;

import java.util.List;

/**
 * 入院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
public interface AdmissionRegistrationService extends IService<AdmissionRegistration> {

    /**
     * 查询入院登记列表
     *
     * @param pageNo   页码
     * @param pageSize 页面大小
     * @return {@link List}<{@link AdmissionRegistration}>
     */
    Page<AdmissionRegistration> selectAdmissionRegistrationList(Long pageNo, Long pageSize, String userName, String identificationNumber);
}

