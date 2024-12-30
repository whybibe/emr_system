package com.qingfeng.electronic.modules.back.information.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.entity.EnumsRo;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospNoticeSaveDTO;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospNoticeUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.HospNotice;
import com.qingfeng.electronic.modules.back.information.domain.enums.NoticeTypeEnum;
import com.qingfeng.electronic.modules.back.information.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 医院公告信息表
 *
 * @author 王淮洋
 * @date 2023-04-14 14:47:17
 */
@Api(tags = "医院公告信息表管理")
@RestController
@RequestMapping("/admin/msg/hospNotice")
@Validated
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService hospNoticeService;

    @PreAuthorize("hasAuthority('bnt.notice.list')")
    @ApiOperation(value = "医院公告信息表分页列表", notes = "医院公告信息表分页列表")
    @GetMapping("/list")
    @Log(title = "医院公告信息表", businessType = BusinessType.SELECT, message = "医院公告信息表分页列表")
    public Result<Page<HospNotice>> list(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @RequestParam("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @RequestParam("limit") Long limit,
            @ApiParam(name = "title", value = "标题", required = false)
            @RequestParam("title") String title,
            @ApiParam(name = "noticeType", value = "公告类型", required = false)
            @RequestParam(value = "noticeType", required = false) NoticeTypeEnum noticeType){
        Page<HospNotice> hospNoticeList = hospNoticeService.findHospNoticeByPage(page, limit, title, noticeType);
        return Result.ok(hospNoticeList);
    }

    @PreAuthorize("hasAuthority('bnt.notice.list')")
    @ApiOperation(value = "医院公告信息表信息", notes = "医院公告信息表信息")
    @GetMapping("/info/{id}")
    @Log(title = "医院公告信息表", businessType = BusinessType.SELECT, message = "医院公告信息表信息")
    public Result<HospNotice> info(@PathVariable("id") Long id){
		HospNotice hospNotice = hospNoticeService.getById(id);
        return Result.ok(hospNotice);
    }

    @PreAuthorize("hasAuthority('bnt.notice.save')")
    @ApiOperation(value = "保存医院公告信息表信息", notes = "保存医院公告信息表信息")
    @PostMapping("/save")
    @Log(title = "医院公告信息表", businessType = BusinessType.INSERT, message = "保存医院公告信息表信息")
    public Result save(@RequestBody @Validated HospNoticeSaveDTO hospNoticeSaveDTO){
		hospNoticeService.saveHospNotice(hospNoticeSaveDTO, getUserId(), getUserName());
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.notice.update')")
    @ApiOperation(value = "修改医院公告信息", notes = "修改医院公告信息")
    @PutMapping("/update")
    @Log(title = "医院公告信息表", businessType = BusinessType.UPDATE, message = "修改医院公告信息表信息")
    public Result update(@RequestBody @Validated HospNoticeUpdateDTO hospNoticeUpdateDTO){
		hospNoticeService.updateHospNoticeById(hospNoticeUpdateDTO, getUserId(), getUserName());
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.notice.delete')")
    @ApiOperation(value = "删除医院公告信息表信息", notes = "删除医院公告信息表信息")
    @DeleteMapping("/delete/{id}")
    @Log(title = "医院公告信息表", businessType = BusinessType.DELETE, message = "删除医院公告信息表信息")
    public Result delete(@PathVariable("id") Long id){
		hospNoticeService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "返回公告类型枚举值", notes = "返回公告类型枚举值")
    @GetMapping("/anno/enums")
    @Log(title = "医院公告信息管理", businessType = BusinessType.SELECT, message = "返回公告类型枚举值")
    public Result<List<EnumsRo>> noticeType(){
        return Result.ok(
                Arrays.stream(NoticeTypeEnum.values())
                        .map(n -> EnumsRo.builder()
                                .label(n.getMsg())
                                .value(n.name())
                                .build()
                        )
                        .collect(Collectors.toList())
        );
    }
}
