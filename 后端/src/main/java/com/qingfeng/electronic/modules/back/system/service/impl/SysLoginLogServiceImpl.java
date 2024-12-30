package com.qingfeng.electronic.modules.back.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysLoginLog;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysLoginLogQueryVo;
import com.qingfeng.electronic.modules.back.system.dao.SysLoginLogMapper;
import com.qingfeng.electronic.modules.back.system.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/5
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {


    private SysLoginLogMapper sysLoginLogMapper;

    @Autowired
    public SysLoginLogServiceImpl(SysLoginLogMapper sysLoginLogMapper) {
        this.sysLoginLogMapper = sysLoginLogMapper;
    }

    @Override
    public IPage<SysLoginLog> selectPage(Page<SysLoginLog> pageParam, SysLoginLogQueryVo sysLoginLogQueryVo) {
        return sysLoginLogMapper.selectPage(pageParam, sysLoginLogQueryVo);
    }
}
