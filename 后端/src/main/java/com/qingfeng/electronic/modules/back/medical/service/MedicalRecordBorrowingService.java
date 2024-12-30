package com.qingfeng.electronic.modules.back.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.medical.domain.entity.MedicalRecordBorrowing;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/23
 */
public interface MedicalRecordBorrowingService extends IService<MedicalRecordBorrowing> {

    /**
     * 导出病历
     *
     * @param response 响应
     */
    void exportMedicalRecord(HttpServletResponse response);
}
