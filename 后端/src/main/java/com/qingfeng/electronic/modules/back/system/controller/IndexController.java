package com.qingfeng.electronic.modules.back.system.controller;

import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import com.qingfeng.electronic.base.util.utils.JwtHelper;
import com.qingfeng.electronic.base.util.utils.MD5;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUser;
import com.qingfeng.electronic.modules.back.system.domain.vo.LoginVo;
import com.qingfeng.electronic.modules.back.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 后台登录登出
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/2
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    private SysUserService sysUserService;

    @Autowired
    public IndexController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @ApiOperation(value = "管理系统登录接口", notes = "管理系统登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        SysUser sysUser = sysUserService.getByUsername(loginVo.getUsername());
        // 判断用户是否存在
        if(Objects.isNull(sysUser)) {
            throw new GeneralAuthException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        // 判断密码是否正确
        if(!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())) {
            throw new GeneralAuthException(ResultCodeEnum.PASSWORD_ERROR);
        }
        // 判断用户状态是否可用
        if(sysUser.getStatus().intValue() == 0) {
            throw new GeneralAuthException(ResultCodeEnum.ACCOUNT_STOP);
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("token", JwtHelper.createToken(sysUser.getId(), sysUser.getUsername()));
        return Result.ok(map);
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        // 获取请求头token字符串
        String username = JwtHelper.getUsername(request.getHeader("token"));
        Map<String, Object> map = sysUserService.getUserInfo(username);
        return Result.ok(map);
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }

}
