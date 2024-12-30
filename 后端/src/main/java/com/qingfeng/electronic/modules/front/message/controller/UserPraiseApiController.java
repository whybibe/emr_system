package com.qingfeng.electronic.modules.front.message.controller;

import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.front.message.domain.entity.UserPraise;
import com.qingfeng.electronic.modules.front.message.service.UserPraiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 用户点赞关系
 *
 * @author 王淮洋
 * @date 2023-05-12 01:34:10
 */
@Api(tags = "用户点赞关系管理")
@RestController
@RequestMapping("/api/info/UserPraise")
@CrossOrigin
@Validated
public class UserPraiseApiController extends BaseController {

    @Autowired
    private UserPraiseService userPraiseService;

    @ApiOperation(value = "用户点赞关系分页列表", notes = "用户点赞关系分页列表")
    @GetMapping("/list")
    @Log(title = "用户点赞关系", businessType = BusinessType.SELECT, message = "用户点赞关系分页列表")
    public Result list(){

        return Result.ok();
    }

    @ApiOperation(value = "用户点赞关系信息", notes = "用户点赞关系信息")
    @GetMapping("/info/{id}")
    @Log(title = "用户点赞关系", businessType = BusinessType.SELECT, message = "用户点赞关系信息")
    public Result info(@PathVariable("id") Long id){
		UserPraise userPraise = userPraiseService.getById(id);

        return Result.ok();
    }

    @ApiOperation(value = "保存用户点赞关系信息", notes = "保存用户点赞关系信息")
    @PostMapping("/save")
    @Log(title = "用户点赞关系", businessType = BusinessType.SELECT, message = "保存用户点赞关系信息")
    public Result save(@RequestBody UserPraise userPraise){
		userPraiseService.save(userPraise);

        return Result.ok();
    }

    @ApiOperation(value = "修改用户点赞关系信息", notes = "修改用户点赞关系信息")
    @PutMapping("/update")
    @Log(title = "用户点赞关系", businessType = BusinessType.SELECT, message = "修改用户点赞关系信息")
    public Result update(@RequestBody UserPraise userPraise){
		userPraiseService.updateById(userPraise);

        return Result.ok();
    }

    @ApiOperation(value = "删除用户点赞关系信息", notes = "删除用户点赞关系信息")
    @DeleteMapping("/delete")
    @Log(title = "用户点赞关系", businessType = BusinessType.SELECT, message = "删除用户点赞关系信息")
    public Result delete(@RequestBody Long[] ids){
		userPraiseService.removeByIds(Arrays.asList(ids));

        return Result.ok();
    }

}
