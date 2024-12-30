package com.qingfeng.electronic.modules.front.login.controller;

import cn.hutool.core.util.StrUtil;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.file.service.FileService;
import com.qingfeng.electronic.modules.front.login.domain.dto.UserInfoFrontSaveDTO;
import com.qingfeng.electronic.modules.front.login.domain.entity.UserInfo;
import com.qingfeng.electronic.modules.front.login.domain.vo.UserInfoFrontVo;
import com.qingfeng.electronic.modules.front.login.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台用户表
 *
 * @author 王淮洋
 * @date 2023-04-21 23:03:12
 */
@Api(tags = "前台用户表管理")
@RestController
@RequestMapping("/api/info/userInfo")
@Validated
public class UserInfoApiController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private DozerUtils dozerUtils;

    @ApiOperation(value = "根据用户Id查询前台用户信息", notes = "根据用户Id查询前台用户信息")
    @GetMapping("/info/{id}")
    @Log(title = "前台用户管理", businessType = BusinessType.SELECT, message = "根据用户Id查询前台用户信息")
    public Result<UserInfoFrontVo> userInfoById(@PathVariable("id") Long id) {
        return Result.ok(dozerUtils.map2(userInfoService.getById(id), UserInfoFrontVo.class));
    }

    @ApiOperation(value = "根据用户Id查询前台用户信息", notes = "根据用户Id查询前台用户信息")
    @GetMapping("/info/by/{id}")
    @Log(title = "前台用户管理", businessType = BusinessType.SELECT, message = "根据用户Id查询前台用户信息")
    public Result<UserInfo> userInfo(@PathVariable("id") Long id) {
        return Result.ok(userInfoService.getById(id));
    }

    @ApiOperation(value = "保存用户认证信息", notes = "保存用户认证信息")
    @PutMapping("/auth")
    @Log(title = "前台用户管理", businessType = BusinessType.INSERT, message = "保存用户认证信息")
    @Transactional(rollbackFor = GeneralAuthException.class)
    public Result saveUserAuth(@RequestBody @Validated UserInfoFrontSaveDTO userInfoFrontSaveDTO){
        // 先删除之前存在的文件
        UserInfo userInfo = userInfoService.getById(userInfoFrontSaveDTO.getId());
        if (StrUtil.isNotBlank(userInfo.getCertificatesUrl())) {
            fileService.removeFile(userInfo.getCertificatesUrl());
        }
        UserInfo userInfo1 = dozerUtils.map2(userInfoFrontSaveDTO, UserInfo.class);
        userInfo1.setAuthStatus(1);
        userInfoService.updateById(userInfo1);
        return Result.ok();
    }


    @ApiOperation(value = "更新用户基础信息", notes = "更新用户基础信息")
    @PutMapping("/update")
    @Log(title = "前台用户管理", businessType = BusinessType.UPDATE, message = "更新用户基础信息")
    public Result updateUserInfo(@RequestBody UserInfo userInfo){
        userInfoService.updateById(userInfo);
        return Result.ok();
    }


}
