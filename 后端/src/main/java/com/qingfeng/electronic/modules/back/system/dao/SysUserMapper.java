package com.qingfeng.electronic.modules.back.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUser;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/2
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> page, @Param("vo") SysUserQueryVo userQueryVo);

    /**
     * 分页查询用户信息
     * @param sysUserPage
     * @param username
     * @param position
     * @return
     */
    IPage<SysUser> selectUserPage(Page<SysUser> sysUserPage,
                                  @Param("username") String username,
                                  @Param("position") String position);
}
