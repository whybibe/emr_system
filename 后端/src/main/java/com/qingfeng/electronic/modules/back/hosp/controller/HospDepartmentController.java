package com.qingfeng.electronic.modules.back.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.hosp.domain.dto.HospDepartmentSaveDTO;
import com.qingfeng.electronic.modules.back.hosp.domain.dto.HospDepartmentUpdateDTO;
import com.qingfeng.electronic.modules.back.hosp.domain.dto.HospDepartmentUpdateStatusDTO;
import com.qingfeng.electronic.modules.back.hosp.domain.entity.HospDepartment;
import com.qingfeng.electronic.modules.back.hosp.domain.vo.DepPatientVo;
import com.qingfeng.electronic.modules.back.hosp.domain.vo.HospDepartmentVo;
import com.qingfeng.electronic.modules.back.hosp.service.HospDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * restFull
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/31
 */
@Api(tags = "科室管理模块")
@RestController
@RequestMapping("/admin/hosp/hospDepartment")
@Validated
public class HospDepartmentController extends BaseController {

    private HospDepartmentService hospDepartmentService;

    @Autowired
    public HospDepartmentController(HospDepartmentService hospDepartmentService) {
        this.hospDepartmentService = hospDepartmentService;
    }

    @ApiOperation(value = "查询科室门诊列表", notes = "查询科室门诊列表")
    @GetMapping("/list")
    @Log(title = "科室门诊管理", businessType = BusinessType.SELECT, message = "查询科室门诊列表")
    public Result<List<HospDepartmentVo>> list(@RequestParam(value = "depName", required = false) String depName,
                                               @RequestParam(value = "patientName", required = false) String patientName) {
        List<HospDepartmentVo> hospDepartmentVoList = hospDepartmentService.hospDepartmentList(depName, patientName);
        return Result.ok(hospDepartmentVoList);
    }

    @ApiOperation(value = "保存科室信息", notes = "保存科室信息")
    @PostMapping("/save")
    public Result<Void> saveHospDepartment(@RequestBody @Validated HospDepartmentSaveDTO hospDepartmentSaveDTO) {
        hospDepartmentService.saveHospDepartment(hospDepartmentSaveDTO);
        return Result.ok();
    }

    @ApiOperation(value = "修改科室门诊信息", notes = "修改科室门诊信息")
    @PutMapping("/update")
    public Result<Void> updateHospDepartment(@RequestBody @Validated HospDepartmentUpdateDTO hospDepartmentUpdateDTO) {
        hospDepartmentService.updateHospDepartment(hospDepartmentUpdateDTO);
        return Result.ok();
    }

    @ApiOperation(value = "根据科室删除相关的信息", notes = "根据科室删除相关的信息")
    @DeleteMapping("/delete/dep")
    public Result<Boolean> deleteDep(@RequestParam("depName") @NotBlank String depName) {
        return Result.ok(hospDepartmentService.remove(
                new LambdaQueryWrapper<HospDepartment>()
                        .eq(HospDepartment::getDepName, depName)
        ));
    }

    @ApiOperation(value = "根据id删除科室信息", notes = "根据id删除科室信息")
    @DeleteMapping("/delete")
    public Result<Boolean> deleteHospDepartment(@RequestParam("id") @NotNull Long id) {
        return Result.ok(hospDepartmentService.removeById(id));
    }

    @ApiOperation(value = "查询全部的科室信息", notes = "查询全部的科室信息")
    @GetMapping("/findAll/dep")
    public Result<List<String>> findDep() {
        return Result.ok(
                hospDepartmentService.list()
                        .stream()
                        .map(HospDepartment::getDepName)
                        .distinct()
                        .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "根据科室名查询门诊信息", notes = "根据科室名查询门诊信息")
    @GetMapping("/find/patientName")
    public Result<List<String>> findPatientName(@RequestParam(value = "depName", required = true) @NotBlank String depName) {
        return Result.ok(
                hospDepartmentService.list(
                                new LambdaQueryWrapper<HospDepartment>()
                                        .eq(HospDepartment::getDepName, depName)
                        )
                        .stream()
                        .map(HospDepartment::getPatientName)
                        .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "修改科室门诊状态", notes = "修改科室门诊状态")
    @PutMapping("/update/status")
    public Result updateStatus(@RequestBody HospDepartmentUpdateStatusDTO hospDepartmentUpdateStatusDTO) {
        hospDepartmentService.updateStatus(hospDepartmentUpdateStatusDTO);
        return  Result.ok();
    }

    @ApiOperation(value = "获取科室门诊信息树", notes = "获取科室门诊信息树")
    @GetMapping("/tree/dep/patient")
    public Result<List<DepPatientVo>> treeDepPatient() {
        List<DepPatientVo> depPatientVoList = hospDepartmentService.treeDepPatient();
        return Result.ok(depPatientVoList);
    }

    @ApiOperation(value = "导出科室门诊模板", notes = "导出科室门诊模板")
    @GetMapping("/excel/export_template")
    public void exportTemplate(HttpServletResponse response) throws IOException {
        hospDepartmentService.exportTemplate(response);
    }

    @ApiOperation(value = "导出科室门诊模板", notes = "导出科室门诊模板")
    @GetMapping("/excel/export_department")
    public void exportDepartment(HttpServletResponse response) throws IOException {
        hospDepartmentService.exportDepartment(response);
    }

    @ApiOperation(value = "导入科室门诊", notes = "导入科室门诊")
    @PostMapping("/excel/import/department")
    public Result importDepartment(MultipartFile file) throws IOException {
        hospDepartmentService.importDepartment(file);
        return Result.ok();
    }
}
