package com.qingfeng.electronic.modules.back.file.controller;

import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import com.qingfeng.electronic.modules.back.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件管理
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/4/7
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/admin/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传用户头像", notes = "上传用户头像")
    @PostMapping("/img/{avter}")
    public Result<String> uploadFile(
            @ApiParam(value = "文件", required = true) MultipartFile file,
            @ApiParam(value = "目录", required = true)
            @PathVariable("avter") String avter) {
        try {
            String fileUrl = fileService.uploadFile(file, avter);
            return Result.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "上传头像失败");
        }
    }

    @ApiOperation(value = "删除上传的文件", notes = "删除上传的文件")
    @DeleteMapping
    public Result fileDelete(@ApiParam(value = "文件", required = true)
                            @RequestParam("fileUrl") String fileUrl) {
        fileService.removeFile(fileUrl);

        //返回r对象
        return Result.ok();
    }

}
