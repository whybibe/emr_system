package com.qingfeng.electronic.modules.front.info.controller;

import cn.hutool.core.collection.CollUtil;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.information.domain.entity.HospitalInfo;
import com.qingfeng.electronic.modules.front.info.service.HospitalInfoApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 医院信息设置表
 *
 * @author 王淮洋
 * @date 2023-04-08 23:43:31
 */
@Api(tags = "医院信息设置表管理")
@RestController
@RequestMapping("/api/info/hospital")
@Validated
public class HospitalInfoApiController extends BaseController {

    @Autowired
    private HospitalInfoApiService hospitalInfoService;

    @ApiOperation(value = "查询医院信息", notes = "查询医院信息")
    @GetMapping("/info")
    @Log(title = "医院信息设置表", businessType = BusinessType.SELECT, message = "查询医院信息")
    public Result<HospitalInfo> info(){
        List<HospitalInfo> list = hospitalInfoService.list(null);
        if (CollUtil.isNotEmpty(list)) {
            return Result.ok(list.get(0));
        }
        return Result.ok(null);
    }
}
