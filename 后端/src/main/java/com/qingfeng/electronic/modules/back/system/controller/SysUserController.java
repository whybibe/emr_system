package com.qingfeng.electronic.modules.back.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import com.qingfeng.electronic.base.util.utils.MD5;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUser;
import com.qingfeng.electronic.modules.back.system.domain.vo.SysUserQueryVo;
import com.qingfeng.electronic.modules.back.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/2
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
@CrossOrigin
public class SysUserController {

    private SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @ApiOperation(value = "获取用户分页列表", notes = "获取用户分页列表")
    @GetMapping("/{page}/{limit}")
    public Result<IPage<SysUser>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "userQueryVo", value = "查询对象", required = false)
                    SysUserQueryVo userQueryVo) {
        IPage<SysUser> pageModel = sysUserService.selectPage(
                new Page<SysUser>(page, limit),
                userQueryVo
        );
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "根据Id获取用户信息", notes = "根据Id获取用户信息")
    @GetMapping("/get/{id}")
    public Result<SysUser> get(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "保存用户信息", notes = "保存用户信息")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser user) {
        // 判断用户是否已经存在
        List<SysUser> sysUserList = sysUserService.list(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, user.getUsername())
        );

        if (!CollectionUtils.isEmpty(sysUserList)) {
            throw new GeneralAuthException(ResultCodeEnum.USER_ALREADY_EXISTS);
        }

        // 对输入的密码进行MD5加密
        user.setPassword(MD5.encrypt(user.getPassword()));
        sysUserService.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysUser user) {
        sysUserService.updateById(user);
        return Result.ok();
    }

    @ApiOperation(value = "根据Id删除用户信息", notes = "根据Id删除用户信息")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysUserService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户状态", notes = "更新用户状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,
                               @PathVariable Integer status) {
        sysUserService.updateStatus(id, status);
        return Result.ok();
    }

}
