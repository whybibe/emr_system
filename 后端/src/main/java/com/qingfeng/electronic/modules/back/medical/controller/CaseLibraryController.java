package com.qingfeng.electronic.modules.back.medical.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/4/5
 */
@Api(tags = "病例库")
@RestController
@RequestMapping("/admin/medical/caseLibrary")
@Validated
public class CaseLibraryController extends BaseController {

    private CaseLibraryService caseLibraryService;
    private AdmissionRegistrationDetailsService admissionRegistrationDetailsService;

    @Autowired
    public CaseLibraryController(CaseLibraryService caseLibraryService,
                                 AdmissionRegistrationDetailsService admissionRegistrationDetailsService) {
        this.caseLibraryService = caseLibraryService;
        this.admissionRegistrationDetailsService = admissionRegistrationDetailsService;
    }

    @ApiOperation(value = "查询所有病例列表", notes = "查询所有病例列表")
    @GetMapping("/list")
    @Log(title = "查询所有病例列表", businessType = BusinessType.SELECT, message = "查询所有病例列表")
    public Result<List<CaseLibrary>> list() {
        return Result.ok(caseLibraryService.list());
    }

    @ApiOperation(value = "分页查询所有病例列表", notes = "分页查询所有病例列表")
    @GetMapping("/list/page")
    @Log(title = "分页查询所有病例列表", businessType = BusinessType.SELECT, message = "分页查询所有病例列表")
    public Result<Page<CaseLibrary>> listPage(@RequestParam("pageNo") Long pageNo,
                                              @RequestParam("pageSize") Long pageSize,
                                              @RequestParam(value = "caseName", required = false) String caseName,
                                              @RequestParam(value = "caseNum", required = false) String caseNum) {
        return Result.ok(
                caseLibraryService.page(
                        new Page<CaseLibrary>(pageNo, pageSize),
                        new LambdaQueryWrapper<CaseLibrary>()
                                .like(StrUtil.isNotBlank(caseName), CaseLibrary::getCaseName, caseName)
                                .like(StrUtil.isNotBlank(caseNum), CaseLibrary::getCaseNum, caseNum)
                )
        );
    }

    @ApiOperation(value = "统计每个病例患者人数", notes = "统计每个病例患者人数")
    @GetMapping("/statistics")
    @Log(title = "统计每个病例患者人数", businessType = BusinessType.SELECT, message = "统计每个病例患者人数")
    public Result<Map<String, Object>> statistics() {
        // 查询所有的病例
        Map<Long, Set<String>> collectMap = admissionRegistrationDetailsService.list()
                .stream()
                .collect(Collectors.groupingBy(
                        AdmissionRegistrationDetails::getUserId,
                        // 取出diseaseName并去重
                        Collectors.mapping(AdmissionRegistrationDetails::getDiseaseName, Collectors.toSet())
                )).entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().flatMap(Collection::stream).collect(Collectors.toSet())
                ));
        // 统计caseLibraryList中的值在collectMap中出现的次数
        Map<String, Long> longMap = caseLibraryService.list().stream()
                .collect(Collectors.toMap
                        (CaseLibrary::getCaseName,
                                caseLibrary -> collectMap.values().stream()
                                        .filter(set -> set.contains(caseLibrary.getCaseName()))
                                        .count()
                        )
                );
        return Result.ok(
                new HashMap<String, Object>(16) {{
                    put("name", longMap.entrySet().stream().map(Entry::getKey).collect(Collectors.toList()));
                    put("count", longMap.entrySet().stream().map(Entry::getValue).collect(Collectors.toList()));
                }}
        );
    }

    @ApiOperation(value = "新增疾病", notes = "新增疾病")
    @PostMapping("/addCaseLibrary")
    public Result addCaseLibrary(@RequestBody CaseLibrary caseLibrary) {
        // 排查疾病编码是否唯一
        List<CaseLibrary> caseLibraryList = caseLibraryService.list(
                new LambdaQueryWrapper<CaseLibrary>()
                        .eq(CaseLibrary::getCaseNum, caseLibrary.getCaseNum())
                        .ne(ObjectUtil.isNotNull(caseLibrary.getId()), CaseLibrary::getId, caseLibrary.getId())
        );
        if (CollUtil.isNotEmpty(caseLibraryList)) {
            return Result.fail("疾病编码已存在");
        }
        if (ObjectUtil.isNull(caseLibrary.getId())) {
            // 添加操作的用户
            caseLibrary.setRegistrarId(getUserId());
            caseLibrary.setRegistrarName(getUserName());
        }
        return Result.ok(caseLibraryService.saveOrUpdate(caseLibrary));
    }

    @ApiOperation(value = "根据Id删除疾病", notes = "根据Id删除疾病")
    @DeleteMapping("/deleteCaseLibrary/{id}")
    public Result deleteCaseLibrary(@PathVariable("id") Long id) {
        return Result.ok(caseLibraryService.removeById(id));
    }

}
