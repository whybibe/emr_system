package com.qingfeng.electronic.modules.back.information.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.entity.EnumsRo;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.information.domain.dto.DoctorInfoUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.DoctorInfo;
import com.qingfeng.electronic.modules.back.information.domain.enums.DoctorPositionEnum;
import com.qingfeng.electronic.modules.back.information.domain.vo.DoctorInfoVo;
import com.qingfeng.electronic.modules.back.information.service.DoctorInfoService;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUser;
import com.qingfeng.electronic.modules.back.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/2
 */
@Api(tags = "医师详情表管理控制层")
@RestController
@RequestMapping("/admin/info/doctorInfo")
@Validated
public class DoctorInfoController extends BaseController {

    private DoctorInfoService doctorInfoService;
    private SysUserService sysUserService;
    private DozerUtils dozerUtils;

    @Autowired
    public DoctorInfoController(DoctorInfoService doctorInfoService,
                                 SysUserService sysUserService,
                                DozerUtils dozerUtils) {
        this.doctorInfoService = doctorInfoService;
        this.sysUserService = sysUserService;
        this.dozerUtils = dozerUtils;
    }

    @ApiOperation("同步医师信息")
    @GetMapping("/refresh")
    @Log(title = "医师信息管理", businessType = BusinessType.STATUS)
    public Result refreshInfo() {
        doctorInfoService.refreshDoctorInfo();
        return Result.ok();
    }

    @ApiOperation("查询医师信息分页列表")
    @GetMapping("/list")
    @Log(title = "医师信息分页管理", businessType = BusinessType.SELECT, message = "医师信息分页列表")
    public Result<DoctorInfoVo> list(@RequestParam("page") Long page,
                                     @RequestParam("limit") Long limit,
                                     @RequestParam("username") String username,
                                     @RequestParam("position") String position) {
        return Result.ok(doctorInfoService.findDoctorInfoPage(page, limit, username, position));
    }

    @ApiOperation("根据Id查询医师信息列表")
    @GetMapping("/info/{id}")
    public Result infoById(@PathVariable("id") Long id) {
        return Result.ok(doctorInfoService.getById(id));
    }

    @ApiOperation("修改医师信息")
    @PutMapping("/update")
    public Result update(@RequestBody @Validated DoctorInfoUpdateDTO doctorInfoUpdateDTO) {
        return Result.ok(
                doctorInfoService.updateById(
                        dozerUtils.map2(doctorInfoUpdateDTO, DoctorInfo.class)
                )
        );
    }

    @ApiOperation("根据科室门诊职称查询医师列表")
    @GetMapping("/anno/list/patient")
    public Result<List<SysUser>> listPatient(@RequestParam("department") String department,
                                                  @RequestParam("patient") String patient,
                                                  @RequestParam("doctorPosition") String doctorPosition) {
        // 根据日期获取今天星期几
        List<DoctorInfo> doctorInfos = doctorInfoService.list(
                new LambdaQueryWrapper<DoctorInfo>()
                        .like(DoctorInfo::getIntroduction, department)
                        .like(DoctorInfo::getIntroduction, patient)
                        .eq(DoctorInfo::getPosition, doctorPosition)
                        .like(DoctorInfo::getWeekday, new SimpleDateFormat("EEEE").format(new Date()))
        );
        if (CollUtil.isEmpty(doctorInfos)) {
            return Result.ok();
        }

        // 查询用户表
        List<SysUser> sysUsers = sysUserService.listByIds(doctorInfos.stream().map(DoctorInfo::getUserId).collect(Collectors.toList()));
        return Result.ok(sysUsers);
    }

    @ApiOperation("返回医师职称枚举值")
    @GetMapping("/anno/patient")
    public Result<List<EnumsRo>> getPatientTitle() {
        return Result.ok(
                Arrays.stream(DoctorPositionEnum.values())
                        .map(p -> EnumsRo.builder()
                                .label(p.getMsg())
                                .value(p.name())
                                .build())
                        .collect(Collectors.toList())
        );
    }

    @ApiOperation("查询所有医师信息")
    @GetMapping("/anno/all")
    public Result<List<SysUser>> getAllDoctorInfo() {
        List<DoctorInfo> list = doctorInfoService.list();
        List<SysUser> sysUsers = sysUserService.listByIds(list.stream().map(DoctorInfo::getUserId).collect(Collectors.toList()));
        return Result.ok(sysUsers);
    }
}
