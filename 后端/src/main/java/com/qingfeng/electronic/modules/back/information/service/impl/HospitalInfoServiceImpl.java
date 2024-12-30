package com.qingfeng.electronic.modules.back.information.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.modules.back.information.dao.HospitalInfoMapper;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospitalInfoSaveDTO;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospitalInfoUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.HospitalInfo;
import com.qingfeng.electronic.modules.back.information.service.HospitalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/30
 */
@Service
public class HospitalInfoServiceImpl extends ServiceImpl<HospitalInfoMapper, HospitalInfo> implements HospitalInfoService {

    private DozerUtils dozerUtils;

    @Autowired
    public HospitalInfoServiceImpl(DozerUtils dozerUtils) {
        this.dozerUtils = dozerUtils;
    }

    /**
     * 保存医院信息实体
     * @param hospitalInfoSaveDTO
     */
    @Override
    public void saveHospitalInfo(HospitalInfoSaveDTO hospitalInfoSaveDTO) {
        baseMapper.insert(dozerUtils.map2(hospitalInfoSaveDTO, HospitalInfo.class));
    }

    /**
     * 保存医院信息
     * @param hospitalInfoUpdateDTO
     */
    @Override
    public void updateHospitalInfo(HospitalInfoUpdateDTO hospitalInfoUpdateDTO) {
        baseMapper.updateById(dozerUtils.map2(hospitalInfoUpdateDTO, HospitalInfo.class));
    }
}
