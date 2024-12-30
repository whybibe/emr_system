package com.qingfeng.electronic.modules.back.information.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospitalInfoSaveDTO;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospitalInfoUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.HospitalInfo;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/30
 */
public interface HospitalInfoService extends IService<HospitalInfo> {

    /**
     * 保存医院信息实体
     * @param hospitalInfoSaveDTO
     */
    void saveHospitalInfo(HospitalInfoSaveDTO hospitalInfoSaveDTO);

    /**
     * 保存医院信息的方法
     * @param hospitalInfoUpdateDTO
     */
    void updateHospitalInfo(HospitalInfoUpdateDTO hospitalInfoUpdateDTO);
}
