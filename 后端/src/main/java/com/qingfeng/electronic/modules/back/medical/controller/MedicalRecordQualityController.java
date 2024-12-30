package com.qingfeng.electronic.modules.back.medical.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.MedicalRecordQuality;
import com.qingfeng.electronic.modules.back.medical.domain.ro.AdmissionRegistrationRo;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.MedicalRecordQualityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/24
 */
@Api(tags = "入院登记表管理")
@RestController
@RequestMapping("/admin/medical/medical_record_quality")
@Validated
public class MedicalRecordQualityController extends BaseController {

    private MedicalRecordQualityService medicalRecordQualityService;
    private AdmissionRegistrationService admissionRegistrationService;
    private DozerUtils dozerUtils;

    @Autowired
    public MedicalRecordQualityController(MedicalRecordQualityService medicalRecordQualityService,
                                          AdmissionRegistrationService admissionRegistrationService,
                                          DozerUtils dozerUtils) {
        this.medicalRecordQualityService = medicalRecordQualityService;
        this.admissionRegistrationService = admissionRegistrationService;
        this.dozerUtils = dozerUtils;
    }

    @ApiOperation(value = "入院登记表分页列表", notes = "入院登记表分页列表")
    @GetMapping("/list")
    @Log(title = "病案质量管理", businessType = BusinessType.SELECT, message = "入院登记表分页列表")
    public Result<Page<AdmissionRegistrationRo>> list(@RequestParam("pageNo") Long pageNo,
                                                      @RequestParam("pageSize") Long pageSize,
                                                      @RequestParam("userName") String userName,
                                                      @RequestParam("identificationNumber") String identificationNumber) {

        Page<AdmissionRegistration> page = admissionRegistrationService.page(
                new Page<AdmissionRegistration>(pageNo, pageSize),
                new LambdaQueryWrapper<AdmissionRegistration>()
                        .like(StrUtil.isNotBlank(userName), AdmissionRegistration::getUserName, userName)
                        .eq(StrUtil.isNotBlank(identificationNumber), AdmissionRegistration::getIdentificationNumber, identificationNumber)
        );

        // 查询所有病案质量评价
        Map<Long, MedicalRecordQuality> collect = medicalRecordQualityService.list(
                        new LambdaQueryWrapper<MedicalRecordQuality>()
                                .in(MedicalRecordQuality::getAdmissionRegistrationId, page.getRecords().stream().map(AdmissionRegistration::getId).collect(Collectors.toList()))
                ).stream()
                .collect(Collectors.toMap(
                        MedicalRecordQuality::getAdmissionRegistrationId,
                        Function.identity()
                ));

        List<AdmissionRegistrationRo> admissionRegistrationRoList = page.getRecords()
                .stream()
                .map(ar -> {
                    AdmissionRegistrationRo admissionRegistrationRo = dozerUtils.map2(ar, AdmissionRegistrationRo.class);
                    admissionRegistrationRo.setMedicalRecordQuality(collect.getOrDefault(ar.getId(), MedicalRecordQuality.builder().build()));
                    return admissionRegistrationRo;
                })
                .collect(Collectors.toList());

        Page<AdmissionRegistrationRo> admissionRegistrationRoPage = new Page<AdmissionRegistrationRo>(page.getCurrent(), page.getSize());
        admissionRegistrationRoPage.setTotal(page.getTotal());
        admissionRegistrationRoPage.setRecords(admissionRegistrationRoList);

        return Result.ok(admissionRegistrationRoPage);
    }

    /**
     * 添加病案质量分析
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加病案质量分析")
    public Result add(@RequestBody MedicalRecordQuality medicalRecordQuality) {
        medicalRecordQuality.setRegistrarId(getUserId());
        medicalRecordQuality.setRegistrarName(getUserName());
        return Result.ok(medicalRecordQualityService.save(medicalRecordQuality));
    }
}
