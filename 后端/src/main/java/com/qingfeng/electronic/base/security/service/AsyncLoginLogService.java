package com.qingfeng.electronic.base.security.service;

/**
 * 异步调用日志服务
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/4/5
 */
public interface AsyncLoginLogService {

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status 状态
     * @param ipaddr ip
     * @param message 消息内容
     * @return
     */
    void recordLoginLog(String username, Integer status, String ipaddr, String message);
}
