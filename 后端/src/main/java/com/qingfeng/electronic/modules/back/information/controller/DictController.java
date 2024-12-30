package com.qingfeng.electronic.modules.back.information.controller;

import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.back.information.domain.dto.DictSaveDTO;
import com.qingfeng.electronic.modules.back.information.domain.dto.DictUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.Dict;
import com.qingfeng.electronic.modules.back.information.domain.vo.DictVo;
import com.qingfeng.electronic.modules.back.information.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/25
 */
@Api(tags = "数据字典表管理控制层")
@RestController
@RequestMapping("/admin/info/dict")
@Valid
public class DictController extends BaseController {

    private DictService dictService;
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public DictController(DictService dictService,
                          StringRedisTemplate stringRedisTemplate) {
        this.dictService = dictService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 获取所有的数据字典列表
     * @return
     */
    @PreAuthorize("hasAnyAuthority('bnt.dict.list')")
    @ApiOperation(value = "获取所有的数据字典列表", notes = "获取所有的数据字典列表")
    @GetMapping("/list")
    @Log(title = "获取所有的数据字典列表", businessType = BusinessType.SELECT)
    public Result<List<DictVo>> dictList() {
        return Result.ok(dictService.findListTree());
    }

    @PreAuthorize("hasAnyAuthority('bnt.dict.save')")
    @ApiOperation(value = "保存数据字典", notes = "保存数据字典")
    @PostMapping("/save")
    public Result<Void> saveDict(@RequestBody @Validated DictSaveDTO dictSaveDTO) {
        dictService.saveDict(dictSaveDTO);
        return Result.ok();
    }

    /**
     * 更新数据字典
     * @param dictUpdateDTO
     * @return
     */
    @PreAuthorize("hasAnyAuthority('bnt.dict.update')")
    @PutMapping("/update")
    public Result<Void> updateDict(@RequestBody @Validated DictUpdateDTO dictUpdateDTO) {
        dictService.updateDictById(dictUpdateDTO);
        return Result.ok();
    }

    @PreAuthorize("hasAnyAuthority('bnt.dict.list')")
    @GetMapping("/info/{dictId}")
    public Result<Dict> getDictInfo(@PathVariable("dictId") Long dictId) {
        return Result.ok(dictService.getById(dictId));
    }

    @PreAuthorize("hasAnyAuthority('bnt.dict.delete')")
    @DeleteMapping("/delete")
    public Result<Void> deleteDict(@RequestBody List<Long> ids) {
        // 清除Redis缓存
        stringRedisTemplate.delete("dict");
        dictService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation(value = "导出数据字典模板", notes = "导出数据字典模板")
    @GetMapping("/anno/excel/export_template")
    @Log(title = "导出数据字典模板", businessType = BusinessType.EXPORT)
    public void exportDictTemplate(HttpServletResponse response) {
        dictService.exportDictTemplate(response);
    }

    @ApiOperation(value = "导出数据字典", notes = "导出数据字典")
    @GetMapping("/anno/excel/export_dict")
    @Log(title = "导出数据字典", businessType = BusinessType.EXPORT)
    public void exportDict(HttpServletResponse response) {
        dictService.exportDict(response);
    }

    @ApiOperation(value = "导入数据字典", notes = "导入数据字典")
    @PostMapping("/anno/excel/import_dict")
    @Log(title = "导入数据字典", businessType = BusinessType.IMPORT)
    public Result<Void> importDict(MultipartFile file) {
        dictService.importDict(file);
        return Result.ok();
    }

    /**
     * 根据大类查询基本数据字典信息，封装Map返回
     */
    @ApiOperation(value = "根据大类查询数据字典", notes = "根据大类查询数据字典")
    @GetMapping("/anno/dw/info")
    @Log(title = "根据大类查询数据字典", businessType = BusinessType.SELECT)
    public Result<Map<String, Map<String, String>>> getDictDwMap() {
        // 查询除省以外的数据字典并封装Map返回
        return Result.ok(dictService.getDictDwMap());
    }

}
