package com.qingfeng.electronic.modules.back.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysOperLog;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysOperLogQueryVo;
import com.qingfeng.electronic.modules.back.system.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/5
 */
@Api(value = "SysOperLog管理", tags = "SysOperLog管理")
@RestController
@RequestMapping(value="/admin/system/sysOperLog")
public class SysOperLogController {

    private SysOperLogService sysOperLogService;

    @Autowired
    public SysOperLogController(SysOperLogService sysOperLogService) {
        this.sysOperLogService = sysOperLogService;
    }

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<SysOperLog>> operLoglist(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "sysOperLogVo", value = "查询对象", required = false)
                    SysOperLogQueryVo sysOperLogQueryVo) {
        Page<SysOperLog> pageParam = new Page<>(page, limit);
        IPage<SysOperLog> pageModel = sysOperLogService.selectPage(pageParam, sysOperLogQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "批量删除操作日志")
    @DeleteMapping
    public Result delete(@RequestBody List<Long> ids) {
        sysOperLogService.removeByIds(ids);
        return Result.ok();
    }
}
