package com.qingfeng.electronic.base.util.utils;

import com.qingfeng.electronic.modules.back.system.domain.entity.SysMenu;
import com.qingfeng.electronic.modules.back.system.domain.vo.MetaVo;
import com.qingfeng.electronic.modules.back.system.domain.vo.RouterVo;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据菜单数据构建路由的工具类
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/4/5
 */
public class RouterHelper {

    /**
     * 根据菜单构建路由
     *
     * @param menus
     * @return
     */
    public static List<RouterVo> buildRouters(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();

        menus.forEach(menu -> {
            RouterVo router = RouterVo.builder()
                    .hidden(false)
                    .alwaysShow(false)
                    .path(getRouterPath(menu))
                    .component(menu.getComponent())
                    .meta(new MetaVo(menu.getName(), menu.getIcon()))
                    .build();
            List<SysMenu> children = menu.getChildren();
            //如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
            if (menu.getType().intValue() == 1) {
                List<SysMenu> hiddenMenuList = children.stream()
                        .filter(item -> !StringUtils.isEmpty(item.getComponent()))
                        .collect(Collectors.toList());
                hiddenMenuList.forEach(hiddenMenu ->
                        routers.add(
                                RouterVo.builder()
                                        .hidden(true)
                                        .alwaysShow(false)
                                        .path(getRouterPath(hiddenMenu))
                                        .component(hiddenMenu.getComponent())
                                        .meta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()))
                                        .build()
                        )
                );

            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if (children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildRouters(children));
                }
            }

            routers.add(router);
        });
        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public static String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if (menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }
}
