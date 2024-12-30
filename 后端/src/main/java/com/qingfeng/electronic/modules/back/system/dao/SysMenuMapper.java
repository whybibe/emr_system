package com.qingfeng.electronic.modules.back.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/2
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户id查询菜单集合
     * @param userId
     * @return
     */
    List<SysMenu> findListByUserId(@Param("userId") Long userId);
}
