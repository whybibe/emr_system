package com.qingfeng.electronic.modules.back.medical.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.medical.domain.dto.MedicalFileDto;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DeathRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.vo.DeathRegistrationVo;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.DeathRegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 死亡登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Api(tags = "死亡登记表管理")
@RestController
@RequestMapping("/admin/medical/death_registration")
@Validated
public class DeathRegistrationController extends BaseController {

    private DeathRegistrationService deathRegistrationService;
    private AdmissionRegistrationService admissionRegistrationService;

    @Autowired
    public DeathRegistrationController(DeathRegistrationService deathRegistrationService,
                                       AdmissionRegistrationService admissionRegistrationService) {
        this.deathRegistrationService = deathRegistrationService;
        this.admissionRegistrationService = admissionRegistrationService;
    }


    /**
     * mvc传值预处理
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    setValue(new SimpleDateFormat("yyyy-MM-dd").parse(text));
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid date format", e);
                }
            }
        });
    }

    @ApiOperation(value = "死亡登记表分页列表", notes = "死亡登记表分页列表")
    @GetMapping("/list")
    @Log(title = "死亡登记表", businessType = BusinessType.SELECT, message = "死亡登记表分页列表")
    public Result<DeathRegistrationVo> listDeathRegistration(@RequestParam("pageNo") Long pageNo,
                                                             @RequestParam("pageSize") Long pageSize,
                                                             @RequestParam("userName") String userName,
                                                             @RequestParam("department") String department,
                                                             @RequestParam("patient") String patient,
                                                             @RequestParam("wardNumber") String wardNumber,
                                                             @RequestParam("bedNumber") String bedNumber,
                                                             @RequestParam(value = "deathDate", required = false) Date deathDate,
                                                             @RequestParam(value = "id", required = false) Long id) {
        // 根据条件进行连表分页查询
        DeathRegistrationVo deathRegistrationVo = deathRegistrationService.selectDeathRegistrationPage(
                pageNo,
                pageSize,
                userName,
                department,
                patient,
                wardNumber,
                bedNumber,
                deathDate,
                id);
        return Result.ok(deathRegistrationVo);
    }

    @ApiOperation(value = "保存死亡登记表信息", notes = "保存死亡登记表信息")
    @PostMapping("/save")
    @Log(title = "死亡登记表", businessType = BusinessType.SELECT, message = "保存死亡登记表信息")
    public Result save(@RequestBody DeathRegistration deathRegistration) {
        deathRegistration.setDeathDate(new Date());
        deathRegistration.setRegistrarId(getUserId());
        deathRegistration.setRegistrarName(getUserName());
        deathRegistrationService.save(deathRegistration);
        return Result.ok();
    }

    @ApiOperation(value = "一键结算", notes = "一键结算")
    @GetMapping("/settleAccounts")
    @Log(title = "出院登记表", businessType = BusinessType.SELECT, message = "一键结算")
    public Result settleAccounts(@RequestParam("id") Integer id) {
        deathRegistrationService.update(
                new LambdaUpdateWrapper<DeathRegistration>()
                        .set(DeathRegistration::getIsSettled, "是")
                        .eq(BaseEntity::getId, id)
        );
        return Result.ok();
    }

    @ApiOperation(value = "删除死亡登记表信息", notes = "删除死亡登记表信息")
    @DeleteMapping("/delete")
    @Log(title = "死亡登记表", businessType = BusinessType.SELECT, message = "删除死亡登记表信息")
    public Result delete(@RequestParam("id") Long id) {
        deathRegistrationService.removeById(id);
        return Result.ok();
    }







    @ApiOperation(value = "死亡登记表信息", notes = "死亡登记表信息")
    @GetMapping("/info/{id}")
    @Log(title = "死亡登记表", businessType = BusinessType.SELECT, message = "死亡登记表信息")
    public Result info(@PathVariable("id") Long id) {
        DeathRegistration deathRegistration = deathRegistrationService.getById(id);
        return Result.ok(deathRegistration);
    }

    @ApiOperation(value = "修改死亡登记表信息", notes = "修改死亡登记表信息")
    @PutMapping("/update")
    @Log(title = "死亡登记表", businessType = BusinessType.SELECT, message = "修改死亡登记表信息")
    public Result update(@RequestBody DeathRegistration deathRegistration) {
        deathRegistrationService.updateById(deathRegistration);
        return Result.ok();
    }

    @ApiOperation(value = "上传病案文件", notes = "上传病案文件")
    @PostMapping("/upload")
    @Log(title = "出院登记表", businessType = BusinessType.SELECT, message = "上传病案文件")
    public Result upload(@RequestBody MedicalFileDto medicalFileDto) {
        admissionRegistrationService.update(
                new LambdaUpdateWrapper<AdmissionRegistration>()
                        .eq(BaseEntity::getId, medicalFileDto.getId())
                        .set(AdmissionRegistration::getFileUrl, medicalFileDto.getFileUrl())
        );
        return Result.ok();
    }

}
