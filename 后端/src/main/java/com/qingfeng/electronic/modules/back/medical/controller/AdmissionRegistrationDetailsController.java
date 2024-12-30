package com.qingfeng.electronic.modules.back.medical.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistrationDetails;
import com.qingfeng.electronic.modules.back.medical.domain.entity.CaseLibrary;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationDetailsService;
import com.qingfeng.electronic.modules.back.medical.service.CaseLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 入院会诊详情
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/19
 */
@Api(tags = "入院会诊详情")
@RestController
@RequestMapping("/admin/medical/admission_registration_details")
@Validated
public class AdmissionRegistrationDetailsController extends BaseController {

    private AdmissionRegistrationDetailsService admissionRegistrationDetailsService;
    private CaseLibraryService caseLibraryService;

    @Autowired
    public AdmissionRegistrationDetailsController(AdmissionRegistrationDetailsService admissionRegistrationDetailsService,
                                                  CaseLibraryService caseLibraryService) {
        this.admissionRegistrationDetailsService = admissionRegistrationDetailsService;
        this.caseLibraryService = caseLibraryService;
    }

    @ApiOperation(value = "入院会诊详情列表", notes = "入院会诊详情列表")
    @GetMapping("/list")
    @Log(title = "入院会诊详情列表", businessType = BusinessType.SELECT, message = "入院会诊详情列表")
    public Result<List<AdmissionRegistrationDetails>> list(@RequestParam("id") Long id) {
        List<AdmissionRegistrationDetails> admissionRegistrationDetailsList = admissionRegistrationDetailsService.list(
                new LambdaQueryWrapper<AdmissionRegistrationDetails>()
                        .eq(AdmissionRegistrationDetails::getAdmissionRegistrationId, id)
        );
        return Result.ok(admissionRegistrationDetailsList);
    }

    @ApiOperation(value = "入院会诊详情详情", notes = "入院会诊详情详情")
    @GetMapping("/info/{id}")
    @Log(title = "入院会诊详情详情", businessType = BusinessType.SELECT, message = "入院会诊详情详情")
    public Result<AdmissionRegistrationDetails> info(@PathVariable("id") Long id) {
        AdmissionRegistrationDetails admissionRegistrationDetails = admissionRegistrationDetailsService.getById(id);
        return Result.ok(admissionRegistrationDetails);
    }

    @ApiOperation(value = "添加入院会诊详情", notes = "添加入院会诊详情")
    @PostMapping("/add")
    @Log(title = "添加入院会诊详情", businessType = BusinessType.INSERT, message = "添加入院会诊详情")
    public Result add(@RequestBody AdmissionRegistrationDetails admissionRegistrationDetails) {
        admissionRegistrationDetails.setUserId(getUserId());
        admissionRegistrationDetails.setUserName(getUserName());
        admissionRegistrationDetailsService.save(admissionRegistrationDetails);

        // 查询所有的病例库
        Set<CaseLibrary> caseSet = caseLibraryService.list()
                .stream()
                .collect(Collectors.toSet());

        admissionRegistrationDetails.getDiseaseName()
                .stream()
                .filter(a -> !caseSet.contains(a))
                .forEach(a -> caseLibraryService.save(
                        CaseLibrary.builder()
                                .registrarId(getUserId())
                                .registrarName(getUserName())
                                .caseName(a)
                                .build()
                ));

        return  Result.ok();
    }

    @ApiOperation(value = "修改入院会诊详情", notes = "修改入院会诊详情")
    @PutMapping("/update")
    @Log(title = "修改入院会诊详情", businessType = BusinessType.UPDATE, message = "修改入院会诊详情")
    public Result update(@RequestBody AdmissionRegistrationDetails admissionRegistrationDetails) {

        // 查询所有的病例库
        Set<CaseLibrary> caseSet = caseLibraryService.list()
                .stream()
                .collect(Collectors.toSet());

        admissionRegistrationDetails.getDiseaseName()
                .stream()
                .filter(a -> !caseSet.contains(a))
                .forEach(a -> caseLibraryService.save(
                        CaseLibrary.builder()
                                .registrarId(getUserId())
                                .registrarName(getUserName())
                                .caseName(a)
                                .build()
                ));

        admissionRegistrationDetailsService.updateById(admissionRegistrationDetails);
        return Result.ok();
    }

    @ApiOperation(value = "删除入院会诊详情", notes = "删除入院会诊详情")
    @DeleteMapping("/delete")
    @Log(title = "删除入院会诊详情", businessType = BusinessType.DELETE, message = "删除入院会诊详情")
    public Result delete(@RequestParam("id") Long id) {
        admissionRegistrationDetailsService.removeById(id);
        return  Result.ok();
    }
}

