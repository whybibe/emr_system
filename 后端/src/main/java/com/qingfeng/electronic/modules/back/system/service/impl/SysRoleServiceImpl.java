package com.qingfeng.electronic.modules.back.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysRole;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUserRole;
import com.qingfeng.electronic.modules.back.system.domain.vo.AssginRoleVo;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysRoleQueryVo;
import com.qingfeng.electronic.modules.back.system.dao.SysRoleMapper;
import com.qingfeng.electronic.modules.back.system.dao.SysUserRoleMapper;
import com.qingfeng.electronic.modules.back.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/1
 */
@Transactional(rollbackFor = GeneralAuthException.class)
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private SysRoleMapper sysRoleMapper;
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysUserRoleMapper sysUserRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    /**
     * 分页获取角色数据
     * @param pageParam
     * @param roleQueryVo
     * @return
     */
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo) {

        return sysRoleMapper.selectPage(pageParam, roleQueryVo);
    }

    /**
     * 根据用户Id获取角色数据
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {
        //获取所有角色
        List<SysRole> roles = sysRoleMapper.selectList(null);
        //根据用户id查询  获取用户已分配的角色
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, userId)
        );
        //获取用户已分配的角色id
        List<Long> userRoleIds = userRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
        //创建返回的Map
        Map<String, Object> returnMap = new HashMap<>(2);
        returnMap.put("allRoles", roles);
        returnMap.put("userRoleIds", userRoleIds);
        return returnMap;
    }

    /**
     * 根据用户分配角色
     * @param assginRoleVo
     */
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除原来分配的角色
        sysUserRoleMapper.delete(
                new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, assginRoleVo.getUserId())
        );
        //获取所有的角色id
        List<Long> roleIdList = assginRoleVo.getRoleIdList();

        roleIdList.stream()
                .filter(roleId -> Objects.nonNull(roleId))
                .forEach(roleId -> {
                            //保存
                            sysUserRoleMapper.insert(
                                    SysUserRole.builder()
                                            .userId(assginRoleVo.getUserId())
                                            .roleId(roleId)
                                            .build()
                            );
                        }

                );
    }
}
