package com.qingfeng.electronic.modules.back.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import com.qingfeng.electronic.base.util.utils.MenuHelper;
import com.qingfeng.electronic.base.util.utils.RouterHelper;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysMenu;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysRoleMenu;
import com.qingfeng.electronic.modules.back.system.domain.vo.AssginMenuVo;
import com.qingfeng.electronic.modules.back.system.domain.vo.RouterVo;
import com.qingfeng.electronic.modules.back.system.dao.SysMenuMapper;
import com.qingfeng.electronic.modules.back.system.dao.SysRoleMenuMapper;
import com.qingfeng.electronic.modules.back.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单管理业务层
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/2
 */
@Transactional(rollbackFor = GeneralAuthException.class)
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private SysMenuMapper sysMenuMapper;
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper, SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
        this.sysRoleMenuMapper = sysRoleMenuMapper;
    }

    @Override
    public List<SysMenu> findNodes() {
        //全部权限列表
        List<SysMenu> sysMenuList = this.list();
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return null;
        }

        //构建树形数据
        List<SysMenu> result = MenuHelper.buildTree(sysMenuList);
        return result;
    }

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取所有status为1的权限列表
        List<SysMenu> menuList = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getStatus, 1)
        );
        //根据角色id获取角色权限
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(
                new LambdaQueryWrapper<SysRoleMenu>()
                        .eq(SysRoleMenu::getRoleId, roleId)
        );
        //获取该角色已分配的所有权限id
        Set<Long> roleMenuIds = roleMenus.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toSet());

        //遍历所有权限列表
        menuList.forEach(sysMenu -> sysMenu.setSelect(roleMenuIds.contains(sysMenu.getId())));

        //将权限列表转换为权限树
        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);
        return sysMenus;
    }

    /**
     * 给角色分配权限
     * @param  assginMenuVo
     */
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //删除已分配的权限
        sysRoleMenuMapper.delete(
                new LambdaQueryWrapper<SysRoleMenu>()
                        .eq(SysRoleMenu::getRoleId, assginMenuVo.getRoleId())
        );
        //遍历所有已选择的权限id
        assginMenuVo.getMenuIdList()
                .forEach(menuId -> {
                    Optional.ofNullable(menuId)
                            .ifPresent(id -> {
                                //添加新权限
                                sysRoleMenuMapper.insert(SysRoleMenu.builder()
                                        .menuId(id)
                                        .roleId(assginMenuVo.getRoleId())
                                        .build());
                            });

                });


    }

    /**
     * 获取用户菜单权限
     * @param userId
     * @return
     */
    @Override
    public List<RouterVo> findUserMenuList(Long userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList;
        if (userId.longValue() == 1) {
            sysMenuList = sysMenuMapper.selectList(
                    new LambdaQueryWrapper<SysMenu>()
                            .eq(SysMenu::getStatus, 1)
                            .orderByAsc(SysMenu::getSortValue)
            );
        } else {
            sysMenuList = sysMenuMapper.findListByUserId(userId);
        }
        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        //构建路由
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);
        return routerVoList;
    }

    /**
     * 根据用户userId获取用户按钮权限
     * @param userId
     * @return
     */
    @Override
    public List<String> findUserPermsList(Long userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList;
        if (userId.longValue() == 1) {
            sysMenuList = sysMenuMapper.selectList(
                    new LambdaQueryWrapper<SysMenu>()
                            .eq(SysMenu::getStatus, 1)
            );
        } else {
            sysMenuList = sysMenuMapper.findListByUserId(userId);
        }

        //创建返回的集合
        return sysMenuList.stream()
                .filter(s -> s.getType() == 2)
                .map(SysMenu::getPerms)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeById(Serializable id) {
        int count = this.count(
                new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getParentId, id)
        );
        if (count > 0) {
            throw new GeneralAuthException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.deleteById(id);
        return false;
    }

}