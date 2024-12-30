package com.qingfeng.electronic.modules.back.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUser;
import com.qingfeng.electronic.modules.back.system.domain.vo.RouterVo;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysUserQueryVo;
import com.qingfeng.electronic.modules.back.system.dao.SysUserMapper;
import com.qingfeng.electronic.modules.back.system.service.SysMenuService;
import com.qingfeng.electronic.modules.back.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/2
 */
@Transactional(rollbackFor = GeneralAuthException.class)
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private SysUserMapper sysUserMapper;
    private SysMenuService sysMenuService;

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper, SysMenuService sysMenuService) {
        this.sysUserMapper = sysUserMapper;
        this.sysMenuService = sysMenuService;
    }

    /**
     * 分页查询用户信息
     *
     * @param pageParam
     * @param userQueryVo
     * @return
     */
    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo userQueryVo) {
        return sysUserMapper.selectPage(pageParam, userQueryVo);
    }

    /**
     * 更新用户状态
     *
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = sysUserMapper.selectById(id);
        sysUser.setStatus(status);
        sysUserMapper.updateById(sysUser);
    }

    /**
     * 根据用户名称查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
        );
    }

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>(16);
        SysUser sysUser = this.getByUsername(username);

        //根据用户id获取菜单权限值
        List<RouterVo> routerVoList = sysMenuService.findUserMenuList(sysUser.getId());
        //根据用户id获取用户按钮权限
        List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());

        result.put("name", sysUser.getName());
        result.put("avatar", sysUser.getHeadUrl());
        //当前权限控制使用不到，我们暂时忽略
        result.put("roles",  new HashSet<>());
        result.put("buttons", permsList);
        result.put("routers", routerVoList);
        return result;
    }

    /**
     * 分页查询用户信息
     * @param sysUserPage
     * @param username
     * @param position
     * @return
     */
    @Override
    public IPage<SysUser> selectUserPage(Page<SysUser> sysUserPage,
                                         String username,
                                         String position) {
        return baseMapper.selectUserPage(sysUserPage, username, position);
    }
}
