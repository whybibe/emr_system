package com.qingfeng.electronic.modules.front.medical;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistrationDetails;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DeathRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DischargeRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.vo.AdmissionRegistrationVo;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationDetailsService;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.DeathRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.DischargeRegistrationService;
import com.qingfeng.electronic.modules.front.login.domain.entity.UserInfo;
import com.qingfeng.electronic.modules.front.login.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * 入院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Api(tags = "入院登记表管理")
@RestController
@RequestMapping("/api/medical/admission_registration")
@Validated
public class AdmissionRegistrationApiController extends BaseController {

    private AdmissionRegistrationService admissionRegistrationService;
    private DischargeRegistrationService dischargeRegistrationService;
    private DeathRegistrationService deathRegistrationService;
    private DozerUtils dozerUtils;
    private UserInfoService userInfoService;
    private AdmissionRegistrationDetailsService admissionRegistrationDetailsService;

    @Autowired
    public AdmissionRegistrationApiController(AdmissionRegistrationService admissionRegistrationService,
                                              DischargeRegistrationService dischargeRegistrationService,
                                              DeathRegistrationService deathRegistrationService,
                                              DozerUtils dozerUtils,
                                              UserInfoService userInfoService,
                                              AdmissionRegistrationDetailsService admissionRegistrationDetailsService) {
        this.admissionRegistrationService = admissionRegistrationService;
        this.dischargeRegistrationService = dischargeRegistrationService;
        this.deathRegistrationService = deathRegistrationService;
        this.dozerUtils = dozerUtils;
        this.userInfoService = userInfoService;
        this.admissionRegistrationDetailsService = admissionRegistrationDetailsService;
    }

    @ApiOperation(value = "入院登记表分页列表", notes = "入院登记表分页列表")
    @GetMapping("/list")
    @Log(title = "入院登记表", businessType = BusinessType.SELECT, message = "入院登记表分页列表")
    public Result<List<AdmissionRegistration>> medicalList(@RequestParam("userId") Long userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        if (ObjectUtil.isNull(userInfo.getCertificatesNo())) {
            return Result.ok(Collections.emptyList());
        }

        List<AdmissionRegistration> admissionRegistrationList = admissionRegistrationService.list(
                new LambdaQueryWrapper<AdmissionRegistration>()
                        .eq(AdmissionRegistration::getIdentificationNumber, userInfo.getCertificatesNo())
        );

        return Result.ok(admissionRegistrationList);
    }

    @ApiOperation(value = "根据ID查询所有病案信息", notes = "根据ID查询所有病案信息")
    @GetMapping("/getById/{id}")
    @Log(title = "入院登记表", businessType = BusinessType.SELECT, message = "根据ID查询所有病案信息")
    public Result<AdmissionRegistrationVo> getById(@PathVariable("id") Long id) {
            return Result.ok(
                    AdmissionRegistrationVo.builder()
                            .admissionRegistration(admissionRegistrationService.getById(id))
                            .admissionRegistrationDetailsList(
                                    admissionRegistrationDetailsService.list(
                                            new LambdaQueryWrapper<AdmissionRegistrationDetails>()
                                                    .eq(AdmissionRegistrationDetails::getAdmissionRegistrationId, id)
                                                    .orderByAsc(AdmissionRegistrationDetails::getContentTime)
                                    )
                            )
                            .dischargeRegistration(dischargeRegistrationService.lambdaQuery().eq(DischargeRegistration::getAdmissionRegistrationId, id).one())
                            .deathRegistration(deathRegistrationService.lambdaQuery().eq(DeathRegistration::getAdmissionRegistrationId, id).one())
                            .build()
            );
    }

}
