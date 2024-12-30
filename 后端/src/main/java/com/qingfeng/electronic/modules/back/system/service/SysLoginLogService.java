package com.qingfeng.electronic.modules.back.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysLoginLog;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysLoginLogQueryVo;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/5
 */
public interface SysLoginLogService extends IService<SysLoginLog> {

    /**
     * 列表显示
     * @param pageParam
     * @param sysLoginLogQueryVo
     * @return
     */
    IPage<SysLoginLog> selectPage(Page<SysLoginLog> pageParam, SysLoginLogQueryVo sysLoginLogQueryVo);
}
