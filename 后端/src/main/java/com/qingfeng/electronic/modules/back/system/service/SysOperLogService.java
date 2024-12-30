package com.qingfeng.electronic.modules.back.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysOperLog;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysOperLogQueryVo;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/5
 */
public interface SysOperLogService extends IService<SysOperLog> {

    IPage<SysOperLog> selectPage(Page<SysOperLog> pageParam, SysOperLogQueryVo sysOperLogQueryVo);
}
