package com.qingfeng.electronic.modules.back.information.controller;

import cn.hutool.core.collection.CollUtil;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospitalInfoSaveDTO;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospitalInfoUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.HospitalInfo;
import com.qingfeng.electronic.modules.back.information.service.HospitalInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/30
 */
@Api(tags = "医院信息管理控制层")
@RestController
@RequestMapping("/admin/info/hospital")
@Valid
public class HospitalInfoController extends BaseController {

    private HospitalInfoService hospitalInfoService;

    @Autowired
    public HospitalInfoController(HospitalInfoService hospitalInfoService) {
        this.hospitalInfoService = hospitalInfoService;
    }

    @ApiOperation(value = "查询医院信息", notes = "查询医院信息")
    @GetMapping("/info")
    public Result<HospitalInfo> findHospitalInfo() {
        List<HospitalInfo> list = hospitalInfoService.list(null);
        if (CollUtil.isNotEmpty(list)) {
            return Result.ok(list.get(0));
        }
        return Result.ok(null);
    }

    @ApiOperation(value = "保存医院信息", notes = "保存医院信息")
    @PostMapping("/save")
    public Result<Void> saveHospitalInfo(@RequestBody @Valid HospitalInfoSaveDTO hospitalInfoSaveDTO) {
        hospitalInfoService.saveHospitalInfo(hospitalInfoSaveDTO);
        return Result.ok();
    }

    @ApiOperation(value = "保存医院信息", notes = "保存医院信息")
    @PutMapping("/update")
    public Result<Void> updateHospitalInfo(@RequestBody @Valid HospitalInfoUpdateDTO hospitalInfoUpdateDTO) {
        hospitalInfoService.updateHospitalInfo(hospitalInfoUpdateDTO);
        return Result.ok();
    }
}
