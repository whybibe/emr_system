package com.qingfeng.electronic.modules.back.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysLoginLog;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysLoginLogQueryVo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/5
 */
@Repository
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    /**
     * 分页查询登录日志信息
     * @param page
     * @param vo
     * @return
     */
    IPage<SysLoginLog> selectPage(Page<SysLoginLog> page, @Param("vo") SysLoginLogQueryVo vo);
}
