package com.qingfeng.electronic.modules.back.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysRole;
import com.qingfeng.electronic.modules.back.system.domain.vo.AssginRoleVo;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysRoleQueryVo;

import java.util.Map;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/1
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色信息
     * @param pageParam
     * @param roleQueryVo
     * @return
     */
    IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo);

    /**
     * 根据用户获取角色数据
     * @param userId
     * @return
     */
    Map<String, Object> getRolesByUserId(Long userId);

    /**
     * 分配角色
     * @param assginRoleVo
     */
    void doAssign(AssginRoleVo assginRoleVo);
}
