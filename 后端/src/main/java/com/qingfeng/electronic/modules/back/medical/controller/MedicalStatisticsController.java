package com.qingfeng.electronic.modules.back.medical.controller;

import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DeathRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DischargeRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.MedicalRecordQuality;
import com.qingfeng.electronic.modules.back.medical.domain.ro.MedicalRecordNumRo;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.DeathRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.DischargeRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.MedicalRecordQualityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/24
 */
@Api(tags = "入院登记表管理")
@RestController
@RequestMapping("/admin/medical/statistics/anno")
@Validated
public class MedicalStatisticsController extends BaseController {

    private AdmissionRegistrationService admissionRegistrationService;
    private DischargeRegistrationService dischargeRegistrationService;
    private DeathRegistrationService deathRegistrationService;
    private MedicalRecordQualityService medicalRecordQualityService;

    @Autowired
    public MedicalStatisticsController(AdmissionRegistrationService admissionRegistrationService,
                                       DischargeRegistrationService dischargeRegistrationService,
                                       DeathRegistrationService deathRegistrationService,
                                       MedicalRecordQualityService medicalRecordQualityService) {
        this.admissionRegistrationService = admissionRegistrationService;
        this.dischargeRegistrationService = dischargeRegistrationService;
        this.deathRegistrationService = deathRegistrationService;
        this.medicalRecordQualityService = medicalRecordQualityService;
    }

    @ApiOperation("统计住院、出院、死亡人数")
    @GetMapping("/medicalRecordNum")
    public Result medicalRecordNum() {
        // 查询所有的病案登记信息
        List<AdmissionRegistration> admissionRegistrationList = admissionRegistrationService.list();
        // 查询所有的出院信息
        List<DischargeRegistration> dischargeRegistrationList = dischargeRegistrationService.list();
        // 查询所有的死亡登记
        List<DeathRegistration> deathRegistrationList = deathRegistrationService.list();

        ArrayList<MedicalRecordNumRo> medicalRecordNumRos = new ArrayList<>();
        medicalRecordNumRos.add(
                MedicalRecordNumRo.builder()
                        .name("住院人数")
                        .y(admissionRegistrationList.size() - dischargeRegistrationList.size() - deathRegistrationList.size())
                        .build()
        );
        medicalRecordNumRos.add(
                MedicalRecordNumRo.builder()
                        .name("出院人数")
                        .y(dischargeRegistrationList.size())
                        .build()
        );
        medicalRecordNumRos.add(
                MedicalRecordNumRo.builder()
                        .name("死亡人数")
                        .y(deathRegistrationList.size())
                        .build()
        );

        return Result.ok(medicalRecordNumRos);
    }

    /**
     * 统计病案质量分析
     */
    @GetMapping("/medicalRecordQuality")
    public Result<ArrayList<Integer>> medicalRecordQuality() {
        // 查询所有的病案质量分析
        Map<Integer, List<MedicalRecordQuality>> collect = medicalRecordQualityService.list()
                .stream()
                .collect(Collectors.groupingBy(MedicalRecordQuality::getScore));

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(collect.getOrDefault(1, Collections.emptyList()).size());
        arrayList.add(collect.getOrDefault(2, Collections.emptyList()).size());
        arrayList.add(collect.getOrDefault(3, Collections.emptyList()).size());
        arrayList.add(collect.getOrDefault(4, Collections.emptyList()).size());
        arrayList.add(collect.getOrDefault(5, Collections.emptyList()).size());
        return Result.ok(arrayList);
    }
}
