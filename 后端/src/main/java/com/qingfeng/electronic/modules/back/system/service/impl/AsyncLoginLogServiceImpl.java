package com.qingfeng.electronic.modules.back.system.service.impl;

import com.qingfeng.electronic.base.security.service.AsyncLoginLogService;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysLoginLog;
import com.qingfeng.electronic.modules.back.system.dao.SysLoginLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/5
 */
@Service
public class AsyncLoginLogServiceImpl implements AsyncLoginLogService {

    private SysLoginLogMapper sysLoginLogMapper;

    @Autowired
    public AsyncLoginLogServiceImpl(SysLoginLogMapper sysLoginLogMapper) {
        this.sysLoginLogMapper = sysLoginLogMapper;
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param ipaddr   ip
     * @param message  消息内容
     * @return
     */
    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {
        sysLoginLogMapper.insert(
                SysLoginLog.builder()
                        .username(username)
                        .ipaddr(ipaddr)
                        .msg(message)
                        .status(status)
                        .build()
        );
    }
}
