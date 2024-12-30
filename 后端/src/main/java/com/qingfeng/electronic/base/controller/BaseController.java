package com.qingfeng.electronic.base.controller;

import com.qingfeng.electronic.base.util.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/12/25
 */
public abstract class BaseController {

    @Autowired
    private HttpServletRequest request;


    /**
     * 获取用户Id的方法
     */
    public Long getUserId() {
        return JwtHelper.getUserId(request.getHeader("token"));
    }

    /**
     * 获取用户名
     */
    public String getUserName() {
        return JwtHelper.getUsername(request.getHeader("token"));
    }

}
