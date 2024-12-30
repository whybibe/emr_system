package com.qingfeng.electronic.modules.back.medical.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.medical.domain.dto.MedicalFileDto;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DischargeRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.vo.DischargeRegistrationVo;
import com.qingfeng.electronic.modules.back.medical.service.AdmissionRegistrationService;
import com.qingfeng.electronic.modules.back.medical.service.DischargeRegistrationService;
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
 * 出院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Api(tags = "出院登记表管理")
@RestController
@RequestMapping("/admin/medical/discharge_registration")
@Validated
public class DischargeRegistrationController extends BaseController {

    private DischargeRegistrationService dischargeRegistrationService;
    private AdmissionRegistrationService admissionRegistrationService;

    @Autowired
    public DischargeRegistrationController(DischargeRegistrationService dischargeRegistrationService,
                                           AdmissionRegistrationService admissionRegistrationService) {
        this.dischargeRegistrationService = dischargeRegistrationService;
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
                    System.out.println(text);
                    setValue(new SimpleDateFormat("yyyy-MM-dd").parse(text));
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid date format", e);
                }
            }
        });
    }


    @ApiOperation(value = "出院登记表分页列表", notes = "出院登记表分页列表")
    @GetMapping("/list")
    @Log(title = "出院登记表", businessType = BusinessType.SELECT, message = "出院登记表分页列表")
    public Result<DischargeRegistrationVo> list(@RequestParam("pageNo") Long pageNo,
                                                @RequestParam("pageSize") Long pageSize,
                                                @RequestParam("userName") String userName,
                                                @RequestParam("department") String department,
                                                @RequestParam("patient") String patient,
                                                @RequestParam("wardNumber") String wardNumber,
                                                @RequestParam("bedNumber") String bedNumber,
                                                @RequestParam(value = "id", required = false) Long id,
                                                @RequestParam(value = "dischargeDate", required = false) Date dischargeDate) {
        // 根据条件进行连表分页查询
        DischargeRegistrationVo dischargeRegistrationVo = dischargeRegistrationService.selectDischargeRegistrationPage(
                pageNo,
                pageSize,
                userName,
                id,
                department,
                patient,
                wardNumber,
                bedNumber,
                dischargeDate);
        return Result.ok(dischargeRegistrationVo);
    }

    @ApiOperation(value = "保存出院登记表信息", notes = "保存出院登记表信息")
    @PostMapping("/save")
    @Log(title = "出院登记表", businessType = BusinessType.SELECT, message = "保存出院登记表信息")
    public Result save(@RequestBody DischargeRegistration dischargeRegistration) {
        // 封装一些基础数据
        dischargeRegistration.setDischargeDate(new Date());
        dischargeRegistration.setRegistrarId(getUserId());
        dischargeRegistration.setRegistrarName(getUserName());
        dischargeRegistrationService.save(dischargeRegistration);
        return Result.ok();
    }

    @ApiOperation(value = "一键结算", notes = "一键结算")
    @GetMapping("/settleAccounts")
    @Log(title = "出院登记表", businessType = BusinessType.SELECT, message = "一键结算")
    public Result settleAccounts(@RequestParam("id") Integer id) {
        dischargeRegistrationService.update(
                new LambdaUpdateWrapper<DischargeRegistration>()
                        .set(DischargeRegistration::getIsSettled, "是")
                        .eq(DischargeRegistration::getId, id)
        );
        return Result.ok();
    }


    @ApiOperation(value = "删除出院登记表信息", notes = "删除出院登记表信息")
    @DeleteMapping("/delete")
    @Log(title = "出院登记表", businessType = BusinessType.SELECT, message = "删除出院登记表信息")
    public Result delete(@RequestParam("id") Integer id) {
        dischargeRegistrationService.removeById(id);
        return Result.ok();
    }



    @ApiOperation(value = "出院登记表信息", notes = "出院登记表信息")
    @GetMapping("/info/{id}")
    @Log(title = "出院登记表", businessType = BusinessType.SELECT, message = "出院登记表信息")
    public Result info(@PathVariable("id") Long id) {
        DischargeRegistration dischargeRegistration = dischargeRegistrationService.getById(id);
        return Result.ok(dischargeRegistration);
    }

    @ApiOperation(value = "修改出院登记表信息", notes = "修改出院登记表信息")
    @PutMapping("/update")
    @Log(title = "出院登记表", businessType = BusinessType.SELECT, message = "修改出院登记表信息")
    public Result update(@RequestBody DischargeRegistration dischargeRegistration) {
        dischargeRegistrationService.updateById(dischargeRegistration);

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
