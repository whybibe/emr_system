package com.qingfeng.electronic.modules.back.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysMenu;
import com.qingfeng.electronic.modules.back.system.domain.vo.AssginMenuVo;
import com.qingfeng.electronic.modules.back.system.domain.vo.RouterVo;

import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/2
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单树形数据
     * @return
     */
    List<SysMenu> findNodes();

    /**
     * 根据角色获取授权权限数据
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param  assginMenuVo
     */
    void doAssign(AssginMenuVo assginMenuVo);

    /**
     * 获取用户菜单权限
     * @param id
     * @return
     */
    List<RouterVo> findUserMenuList(Long id);

    /**
     * 根据用户id获取用户按钮权限
     * @param id
     * @return
     */
    List<String> findUserPermsList(Long id);
}
