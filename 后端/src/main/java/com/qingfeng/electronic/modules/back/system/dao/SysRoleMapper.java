package com.qingfeng.electronic.modules.back.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysRole;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/1
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 条件分页查询
     * @param page
     * @param roleQueryVo
     * @return
     */
    IPage<SysRole> selectPage(
            Page<SysRole> page,
            @Param("vo") SysRoleQueryVo roleQueryVo);
}
