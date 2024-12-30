package com.qingfeng.electronic.modules.back.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysOperLog;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysOperLogQueryVo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/5
 */
@Repository
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

    /**
     * 分页查询操作日志信息
     * @param page
     * @param sysOperLogQueryVo
     * @return
     */
    IPage<SysOperLog> selectPage(Page<SysOperLog> page, @Param("vo") SysOperLogQueryVo vo);

}