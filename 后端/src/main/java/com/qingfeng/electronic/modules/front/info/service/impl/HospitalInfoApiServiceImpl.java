package com.qingfeng.electronic.modules.front.info.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.modules.back.information.domain.entity.HospitalInfo;
import com.qingfeng.electronic.modules.front.info.dao.HospitalInfoApiMapper;
import com.qingfeng.electronic.modules.front.info.service.HospitalInfoApiService;
import org.springframework.stereotype.Service;

/**
 * 医院信息设置表
 *
 * @author 王淮洋
 * @date 2023-04-08 23:43:31
 */
@Service
public class HospitalInfoApiServiceImpl extends ServiceImpl<HospitalInfoApiMapper, HospitalInfo> implements HospitalInfoApiService {

}