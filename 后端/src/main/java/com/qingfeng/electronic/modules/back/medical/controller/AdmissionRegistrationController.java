package com.qingfeng.electronic.modules.back.medical.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.base.util.utils.PaginateListUtil;
import com.qingfeng.electronic.modules.back.medical.domain.dto.AdmissionRegistrationSaveDTO;
import com.qingfeng.electronic.modules.back.medical.domain.dto.AdmissionRegistrationUpdateDTO;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistrationDetails;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DeathRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DischargeRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.vo.AdmissionRegistrationVo;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationDetailsService;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.DeathRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.DischargeRegistrationService;
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

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 入院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Api(tags = "入院登记表管理")
@RestController
@RequestMapping("/admin/medical/admission_registration")
@Validated
public class AdmissionRegistrationController extends BaseController {

    private AdmissionRegistrationService admissionRegistrationService;
    private DischargeRegistrationService dischargeRegistrationService;
    private DeathRegistrationService deathRegistrationService;
    private DozerUtils dozerUtils;
    private AdmissionRegistrationDetailsService admissionRegistrationDetailsService;

    @Autowired
    public AdmissionRegistrationController(AdmissionRegistrationService admissionRegistrationService,
                                           DischargeRegistrationService dischargeRegistrationService,
                                           DeathRegistrationService deathRegistrationService,
                                           DozerUtils dozerUtils,
                                           AdmissionRegistrationDetailsService admissionRegistrationDetailsService) {
        this.admissionRegistrationService = admissionRegistrationService;
        this.dischargeRegistrationService = dischargeRegistrationService;
        this.deathRegistrationService = deathRegistrationService;
        this.dozerUtils = dozerUtils;
        this.admissionRegistrationDetailsService = admissionRegistrationDetailsService;
    }

    @ApiOperation(value = "入院登记表分页列表", notes = "入院登记表分页列表")
    @GetMapping("/list")
    @Log(title = "入院登记表", businessType = BusinessType.SELECT, message = "入院登记表分页列表")
    public Result<Page<AdmissionRegistration>> list(@RequestParam("pageNo") Long pageNo,
                                                    @RequestParam("pageSize") Long pageSize,
                                                    @RequestParam("userName") String userName,
                                                    @RequestParam("identificationNumber") String identificationNumber,
                                                    @RequestParam(value = "id", required = false) Long id) {
        Result<List<AdmissionRegistration>> listResult = this.listNotOut(userName, identificationNumber, id);
        if (CollUtil.isEmpty(listResult.getData())) {
            Page<AdmissionRegistration> admissionRegistrationList
                    = new Page<>(pageNo, pageSize, 0);
            admissionRegistrationList.setRecords(Collections.EMPTY_LIST);
            return Result.ok(admissionRegistrationList);
        }
        List<List<AdmissionRegistration>> lists = new PaginateListUtil<AdmissionRegistration>()
                .paginateList(listResult.getData(), pageSize.intValue());
        List<AdmissionRegistration> admissionRegistrations = lists.get(pageNo.intValue() - 1);
        Page<AdmissionRegistration> admissionRegistrationList = new Page<>(pageNo, pageSize, listResult.getData().size());
        admissionRegistrationList.setRecords(admissionRegistrations);
        return Result.ok(admissionRegistrationList);
    }

    @ApiOperation(value = "查询所有未出院的病人", notes = "查询所有未出院的病人")
    @GetMapping("/listNotOut")
    @Log(title = "入院登记表", businessType = BusinessType.SELECT, message = "查询所有未出院的病人")
    public Result<List<AdmissionRegistration>> listNotOut(@RequestParam(value = "userName", required = false) String userName,
                                                          @RequestParam(value = "identificationNumber", required = false) String identificationNumber,
                                                          @RequestParam(value = "id", required = false) Long id) {
        // 查询所有的出院登记记录
        Set<Long> dischIds = dischargeRegistrationService.list()
                .stream()
                .map(DischargeRegistration::getAdmissionRegistrationId)
                .collect(Collectors.toSet());
        // 查询所有有死亡登记的人
        Set<Long> deathIds = deathRegistrationService.list()
                .stream()
                .map(DeathRegistration::getAdmissionRegistrationId)
                .collect(Collectors.toSet());

        // 查询所有的未出院的病人信息
        List<AdmissionRegistration> list = admissionRegistrationService.list(
                new LambdaQueryWrapper<AdmissionRegistration>()
                        .notIn(CollUtil.isNotEmpty(dischIds), AdmissionRegistration::getId, dischIds)
                        .notIn(CollUtil.isNotEmpty(deathIds), AdmissionRegistration::getId, deathIds)
                        .eq(ObjectUtil.isNotNull(id), AdmissionRegistration::getId, id)
                        .eq(StrUtil.isNotBlank(userName), AdmissionRegistration::getUserName, userName)
                        .eq(StrUtil.isNotBlank(identificationNumber), AdmissionRegistration::getIdentificationNumber, identificationNumber)
        );

        return Result.ok(list);
    }


    @ApiOperation(value = "入院登记表信息", notes = "入院登记表信息")
    @GetMapping("/info/{id}")
    @Log(title = "入院登记表", businessType = BusinessType.SELECT, message = "入院登记表信息")
    public Result<AdmissionRegistration> info(@PathVariable("id") Long id) {
        AdmissionRegistration admissionRegistration = admissionRegistrationService.getById(id);
        return Result.ok(admissionRegistration);
    }

    /**
     * 根据身份Id确认用户住院信息是否已经存在，是否已经出院，住院日期只能是当天及之前比如补住院登记
     *
     * @param admissionRegistrationSaveDTO
     * @return
     */
    @ApiOperation(value = "保存入院登记表信息", notes = "保存入院登记表信息")
    @PostMapping("/save")
    @Log(title = "入院登记表", businessType = BusinessType.SELECT, message = "保存入院登记表信息")
    public Result save(@RequestBody @Valid AdmissionRegistrationSaveDTO admissionRegistrationSaveDTO) {
        // 1. 先判断入院登记日期是否合规
        if (admissionRegistrationSaveDTO.getRegistrationDate().compareTo(new Date()) > 0) {
            return Result.fail("入院登记表日期不能大于当前时间");
        }
        // 2. 先查询入院登记表是否该用户已经存在
        List<AdmissionRegistration> admissionRegistrationList = admissionRegistrationService.list(
                new LambdaQueryWrapper<AdmissionRegistration>()
                        .eq(AdmissionRegistration::getIdentificationNumber, admissionRegistrationSaveDTO.getIdentificationNumber())
                        .le(AdmissionRegistration::getRegistrationDate, admissionRegistrationSaveDTO.getRegistrationDate())
        );
        if (CollUtil.isNotEmpty(admissionRegistrationList)) {
            // 3. 再查询用户是否都已经出院
            List<DischargeRegistration> dischargeRegistrationList = dischargeRegistrationService.list(
                    new LambdaQueryWrapper<DischargeRegistration>()
                            .in(DischargeRegistration::getAdmissionRegistrationId, admissionRegistrationList.stream()
                                    .map(BaseEntity::getId)
                                    .collect(Collectors.toList())
                            )
            );

            if (admissionRegistrationList.size() != dischargeRegistrationList.size()) {
                // 用户已经有住院登记了
                return Result.fail("该用户已经有住院登记了，不可重复登记！！！");
            }
        }
        admissionRegistrationService.save(dozerUtils.map2(admissionRegistrationSaveDTO, AdmissionRegistration.class));
        return Result.ok();
    }

    /**
     * 修改住院，住院时间不能修改
     *
     * @param admissionRegistrationUpdateDTO
     * @return
     */
    @ApiOperation(value = "修改入院登记表信息", notes = "修改入院登记表信息")
    @PutMapping("/update")
    @Log(title = "入院登记表", businessType = BusinessType.SELECT, message = "修改入院登记表信息")
    public Result update(@RequestBody @Valid AdmissionRegistrationUpdateDTO admissionRegistrationUpdateDTO) {
        // 校验一下当前住院时间是否合规，根据Id查询一下之前的信息
        AdmissionRegistration admissionRegistration = admissionRegistrationService.getById(admissionRegistrationUpdateDTO.getId());
        // 判断一下时间有没有变化
        if (admissionRegistrationUpdateDTO.getRegistrationDate().compareTo(admissionRegistration.getRegistrationDate()) != 0) {
            return Result.fail("入院登记表日期不能修改！！！");
        }
        admissionRegistrationService.updateById(dozerUtils.map2(admissionRegistrationUpdateDTO, AdmissionRegistration.class));
        return Result.ok();
    }

    /**
     * 住院登记不能轻易删除，是用户档案，只能当前建当天产出
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除入院登记表信息", notes = "删除入院登记表信息")
    @DeleteMapping("/delete")
    @Log(title = "入院登记表", businessType = BusinessType.SELECT, message = "删除入院登记表信息")
    public Result delete(@RequestBody Long[] ids) {
        // 删除的时候，只能限定今天建，今天删
        List<AdmissionRegistration> collect = admissionRegistrationService.listByIds(Arrays.asList(ids))
                .stream()
                .filter(a -> a.getRegistrationDate().compareTo(new Date()) != 0)
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return Result.fail("只能删除当天因为误操作创建的入院登记信息!");
        }
        // 可以正常删除
        admissionRegistrationService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

    /**
     * 按病案类型查询病案信息
     */
    @ApiOperation(value = "按病案类型查询病案信息", notes = "按病案类型查询病案信息")
    @GetMapping("/getAdmissionRegistrationByCaseType")
    public Result<List<AdmissionRegistration>> getAdmissionRegistrationByCaseType(@RequestParam("medicalType") String medicalType) {
        List<Long> ids = Collections.emptyList();
        if ("出院病案".equals(medicalType)) {
            ids = dischargeRegistrationService.list()
                    .stream()
                    .map(DischargeRegistration::getAdmissionRegistrationId)
                    .collect(Collectors.toList());
        } else if("死亡病案".equals(medicalType)) {
            ids = deathRegistrationService.list()
                    .stream()
                    .map(DeathRegistration::getAdmissionRegistrationId)
                    .collect(Collectors.toList());
        }

        if (CollUtil.isEmpty(ids)) {
            return Result.ok(Collections.emptyList());
        }

        return Result.ok(admissionRegistrationService.listByIds(ids));
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
