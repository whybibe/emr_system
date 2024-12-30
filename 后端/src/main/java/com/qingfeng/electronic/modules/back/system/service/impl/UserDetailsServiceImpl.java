package com.qingfeng.electronic.modules.back.system.service.impl;

import com.qingfeng.electronic.base.security.custom.CustomUser;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUser;
import com.qingfeng.electronic.modules.back.system.service.SysMenuService;
import com.qingfeng.electronic.modules.back.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/5
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private SysUserService sysUserService;
    private SysMenuService sysMenuService;

    @Autowired
    public UserDetailsServiceImpl(SysUserService sysUserService, SysMenuService sysMenuService) {
        this.sysUserService = sysUserService;
        this.sysMenuService = sysMenuService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        if (sysUser.getStatus() == 0) {
            throw new RuntimeException("账号已停用");
        }
        List<String> userPermsList = sysMenuService.findUserPermsList(sysUser.getId());

        return new CustomUser(
                sysUser,
                userPermsList.stream()
                        .map(u -> new SimpleGrantedAuthority(u.trim()))
                        .collect(Collectors.toList())
        );
    }
}