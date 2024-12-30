package com.qingfeng.electronic.modules.front.login.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.front.login.domain.dto.UserInfoUpdateDTO;
import com.qingfeng.electronic.modules.front.login.domain.entity.UserInfo;
import com.qingfeng.electronic.modules.front.login.domain.vo.LoginVo;

import java.util.Map;

/**
 * 患者用户表
 *
 * @author 王淮洋
 * @date 2023-04-21 23:03:12
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 发送验证码
     * @param account
     * @return
     */
    void sendCode(String account);

    /**
     * 前台用户登录
     * @param loginVo
     * @return
     */
    Map<String, Object> userLogin(LoginVo loginVo);


    /**
     * 根据openid查询信息是否已经存在
     * @param openid
     * @return
     */
    UserInfo selectWxInfoOpenId(String openid);

    /**
     * 分页查询用户信息
     * @param userInfoPage
     * @param certificatesType
     * @param authStatus
     * @return
     */
    Page<UserInfo> userInfoPage(Page<UserInfo> userInfoPage, String certificatesType, Integer authStatus);

    /**
     * 用户认证
     * @param userInfoUpdateDTO
     */
    void userAuth(UserInfoUpdateDTO userInfoUpdateDTO);
}

