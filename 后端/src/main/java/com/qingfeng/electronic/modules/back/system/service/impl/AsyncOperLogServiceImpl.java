package com.qingfeng.electronic.modules.back.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.base.log.service.AsyncOperLogService;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysOperLog;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysOperLogQueryVo;
import com.qingfeng.electronic.modules.back.system.dao.SysOperLogMapper;
import com.qingfeng.electronic.modules.back.system.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/5
 */
@Service
public class AsyncOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements AsyncOperLogService, SysOperLogService {

    private SysOperLogMapper sysOperLogMapper;

    @Autowired
    public AsyncOperLogServiceImpl(SysOperLogMapper sysOperLogMapper) {
        this.sysOperLogMapper = sysOperLogMapper;
    }

    @Override
    @Async
    public void saveSysLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }

    @Override
    public IPage<SysOperLog> selectPage(Page<SysOperLog> pageParam, SysOperLogQueryVo sysOperLogQueryVo) {
        return sysOperLogMapper.selectPage(pageParam, sysOperLogQueryVo);
    }
}
