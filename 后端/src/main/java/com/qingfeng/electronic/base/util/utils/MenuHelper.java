package com.qingfeng.electronic.base.util.utils;

import com.qingfeng.electronic.modules.back.system.domain.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据菜单数据构建菜单树的工具类
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/4/2
 */
public class MenuHelper {

    /**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        return sysMenuList.stream()
                .filter(s -> s.getParentId().longValue() == 0)
                .map(s -> findChildren(s, sysMenuList))
                .collect(Collectors.toList());
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        sysMenu.setChildren(new ArrayList<SysMenu>());
        treeNodes.stream()
                .filter(it -> sysMenu.getId().longValue() == it.getParentId().longValue())
                .forEach(it -> {
                    if (sysMenu.getChildren() == null) {
                        sysMenu.setChildren(new ArrayList<>());
                    }
                    sysMenu.getChildren().add(findChildren(it, treeNodes));
                });
        return sysMenu;
    }
}