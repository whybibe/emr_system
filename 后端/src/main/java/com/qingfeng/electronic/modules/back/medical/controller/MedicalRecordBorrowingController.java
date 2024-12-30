package com.qingfeng.electronic.modules.back.medical.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.medical.domain.entity.MedicalRecordBorrowing;
import com.qingfeng.electronic.modules.back.medical.service.MedicalRecordBorrowingService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/23
 */
@Api(tags = "入院登记表管理")
@RestController
@RequestMapping("/admin/medical/medical_record_borrowing")
@Validated
public class MedicalRecordBorrowingController extends BaseController {

    private MedicalRecordBorrowingService medicalRecordBorrowingService;

    @Autowired
    public MedicalRecordBorrowingController(MedicalRecordBorrowingService medicalRecordBorrowingService) {
        this.medicalRecordBorrowingService = medicalRecordBorrowingService;
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
                    setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(text));
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid date format", e);
                }
            }
        });

        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });
    }

    @ApiOperation(value = "分页查询所有病案借阅记录", notes = "分页查询所有病案借阅记录")
    @GetMapping("/listAll")
    @Log(title = "病案借阅", businessType = BusinessType.SELECT, message = "分页查询所有病案借阅记录")
    public Result<Page<MedicalRecordBorrowing>> listAll(@RequestParam("pageNo") Long pageNo,
                                                     @RequestParam("pageSize") Long pageSize,
                                                     @RequestParam("userName") String userName,
                                                     @RequestParam("medicalType") String medicalType,
                                                     @RequestParam("isReturn") String isReturn) {
        return Result.ok(
                medicalRecordBorrowingService.page(
                        new Page<MedicalRecordBorrowing>(pageNo, pageSize),
                        new LambdaQueryWrapper<MedicalRecordBorrowing>()
                                .like(StrUtil.isNotBlank(userName), MedicalRecordBorrowing::getUserName, userName)
                                .eq(StrUtil.isNotBlank(medicalType), MedicalRecordBorrowing::getMedicalType, medicalType)
                                .eq(StrUtil.isNotBlank(isReturn), MedicalRecordBorrowing::getIsReturn, isReturn)
                                .orderByDesc(BaseEntity::getCreateTime)
                )
        );
    }

    @ApiOperation(value = "查询个人全部借阅记录", notes = "查询个人全部借阅记录")
    @GetMapping("/listByUserId")
    @Log(title = "病案借阅", businessType = BusinessType.SELECT, message = "查询个人全部借阅记录")
    public Result<Page<MedicalRecordBorrowing>> listByUserId(@RequestParam("pageNo") Long pageNo,
                                                             @RequestParam("pageSize") Long pageSize,
                                                             @RequestParam("medicalType") String medicalType,
                                                             @RequestParam("isReturn") String isReturn) {
        return Result.ok(
                medicalRecordBorrowingService.page(
                        new Page<MedicalRecordBorrowing>(pageNo, pageSize),
                        new LambdaQueryWrapper<MedicalRecordBorrowing>()
                                .eq(MedicalRecordBorrowing::getUserId, getUserId())
                                .eq(StrUtil.isNotBlank(medicalType), MedicalRecordBorrowing::getMedicalType, medicalType)
                                .eq(StrUtil.isNotBlank(isReturn), MedicalRecordBorrowing::getIsReturn, isReturn)
                                .orderByDesc(BaseEntity::getCreateTime)
                )
        );
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增借阅记录", notes = "传入medicalRecordBorrowing")
    @Log(title = "病案借阅", businessType = BusinessType.SELECT, message = "新增借阅记录")
    public Result addMedicalRecord(@RequestBody MedicalRecordBorrowing medicalRecordBorrowing) {
        // 设置一下默认值
        medicalRecordBorrowing.setIsReturn("否");
        medicalRecordBorrowing.setUserId(getUserId());
        medicalRecordBorrowing.setUserName(getUserName());
        medicalRecordBorrowingService.save(medicalRecordBorrowing);
        return  Result.ok();
    }

    @PostMapping("/return/{id}")
    @ApiOperation(value = "归还病案", notes = "传入id")
    @Log(title = "病案归还", businessType = BusinessType.SELECT, message = "归还病案")
    public Result returnMedicalRecord(@PathVariable Long id) {
        // 根据ID进行修改
        medicalRecordBorrowingService.update(
                new LambdaUpdateWrapper<MedicalRecordBorrowing>()
                        .set(MedicalRecordBorrowing::getIsReturn, "是")
                        .eq(MedicalRecordBorrowing::getId, id)
        );
        return   Result.ok();
    }

    /**
     * 根据ID一键删除
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据ID一键删除", notes = "传入id")
    @Log(title = "病案借阅", businessType = BusinessType.DELETE, message = "根据ID一键删除")
    public Result deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordBorrowingService.removeById(id);
        return  Result.ok();
    }

    /**
     * 导出所有未归还的名单
     */
    @GetMapping("/export/excel")
    public void exportMedicalRecord(HttpServletResponse response) {
        medicalRecordBorrowingService.exportMedicalRecord(response);
    }
}

