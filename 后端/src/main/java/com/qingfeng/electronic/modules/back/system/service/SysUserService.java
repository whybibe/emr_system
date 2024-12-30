package com.qingfeng.electronic.modules.back.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUser;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysUserQueryVo;

import java.util.Map;

/**
 * 用户管理业务层
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/2
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户信息
     * @param pageParam
     * @param adminQueryVo
     * @return
     */
    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo adminQueryVo);

    /**
     * 更新用户状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Integer status);

    /**
     * 根据用户名称进行查询
     * @param username
     * @return
     */
    SysUser getByUsername(String username);

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 分页查询用户信息
     * @param sysUserPage
     * @param username
     * @param position
     * @return
     */
    IPage<SysUser> selectUserPage(Page<SysUser> sysUserPage, String username, String position);
}
