package com.qingfeng.electronic.base.log.service;

import com.qingfeng.electronic.modules.back.system.domain.entity.SysOperLog;

/**
 *
 * @author 清风学Java
 * @date 2023/4/5
 * @version 1.0.0
 */
public interface AsyncOperLogService {


    /**
     * 保存系统日志
     *
     * @param operLog 操作日志
     */
    void saveSysLog(SysOperLog operLog);
}
