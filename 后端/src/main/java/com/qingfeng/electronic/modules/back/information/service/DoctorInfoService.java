package com.qingfeng.electronic.modules.back.information.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.information.domain.entity.DoctorInfo;
import com.qingfeng.electronic.modules.back.information.domain.vo.DoctorInfoVo;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/2
 */
public interface DoctorInfoService  extends IService<DoctorInfo> {

    /**
     * 刷新医师信息
     */
    void refreshDoctorInfo();

    /**
     * 分页查询医师信息
     * @param page
     * @param limit
     * @param username
     * @param position
     * @return
     */
    DoctorInfoVo findDoctorInfoPage(Long page,
                                    Long limit,
                                    String username,
                                    String position);
}
