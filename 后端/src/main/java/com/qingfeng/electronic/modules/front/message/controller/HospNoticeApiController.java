package com.qingfeng.electronic.modules.front.message.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.front.message.domain.entity.HospNotice;
import com.qingfeng.electronic.modules.front.message.service.HospNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 医院公告信息表
 *
 * @author 王淮洋
 * @date 2023-04-14 14:47:17
 */
@Api(tags = "医院公告信息表管理")
@RestController
@RequestMapping("/api/msg/hospNotice")
@Validated
public class HospNoticeApiController extends BaseController {

    @Autowired
    private HospNoticeService hospNoticeService;


    @ApiOperation(value = "前五条医院平台公告信息", notes = "前五条医院平台公告信息")
    @GetMapping("/pin/tai/five")
    @Log(title = "医院公告信息", businessType = BusinessType.SELECT, message = "前五条医院平台公告信息")
    public Result<List<HospNotice>> pinTaiNoticeForFive() {
        List<HospNotice> hospNoticeList = hospNoticeService.pinTaiNoticeForFive();
        return Result.ok(hospNoticeList);
    }

    @ApiOperation(value = "分页查询医院平台公告信息", notes = "分页查询医院平台公告信息")
    @GetMapping("/pin/tai/page")
    @Log(title = "医院公告信息", businessType = BusinessType.SELECT, message = "分页查询医院平台公告信息")
    public Result<IPage<HospNotice>> pinTaiNoticePage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @RequestParam("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @RequestParam("limit") Long limit) {
        IPage<HospNotice> iPage = hospNoticeService.pinTaiNoticePage(page, limit);
        return Result.ok(iPage);
    }

    @ApiOperation(value = "前五条医院停诊公告信息", notes = "前五条医院停诊公告信息")
    @GetMapping("/stop/five")
    @Log(title = "医院公告信息", businessType = BusinessType.SELECT, message = "前五条医院停诊公告信息")
    public Result<List<HospNotice>> stopNoticeForFive() {
        List<HospNotice> hospNoticeList = hospNoticeService.stopNoticeForFive();
        return Result.ok(hospNoticeList);
    }

    @ApiOperation(value = "分页查询医院停诊公告信息", notes = "分页查询医院停诊公告信息")
    @GetMapping("/stop/page")
    @Log(title = "医院公告信息", businessType = BusinessType.SELECT, message = "分页查询医院停诊公告信息")
    public Result<IPage<HospNotice>> stopNoticePage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @RequestParam("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @RequestParam("limit") Long limit) {
        IPage<HospNotice> iPage = hospNoticeService.stopNoticePage(page, limit);
        return Result.ok(iPage);
    }

    @ApiOperation(value = "医院公告信息表信息", notes = "医院公告信息表信息")
    @GetMapping("/info/{id}")
    @Log(title = "医院公告信息表", businessType = BusinessType.SELECT, message = "医院公告信息表信息")
    public Result<HospNotice> info(@PathVariable("id") Long id){
        HospNotice hospNotice = hospNoticeService.getById(id);
        return Result.ok(hospNotice);
    }

    @ApiOperation(value = "修改公告阅读量", notes = "修改公告阅读量")
    @PutMapping("/reading/num/{id}")
    @Log(title = "医院公告信息表", businessType = BusinessType.UPDATE, message = "医院公告信息表信息")
    public Result readingNum(@PathVariable("id") Long id){
        hospNoticeService.readingNumById(id);
        return Result.ok();
    }

}
