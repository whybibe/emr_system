package com.qingfeng.electronic.modules.front.message.controller;

import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.front.message.domain.dto.ReplySaveDTO;
import com.qingfeng.electronic.modules.front.message.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 回复表
 *
 * @author 王淮洋
 * @date 2023-05-13 17:50:14
 */
@Api(tags = "回复表管理")
@RestController
@RequestMapping("/api/info/reply")
@CrossOrigin
@Validated
public class ReplyApiController extends BaseController {

    @Autowired
    private ReplyService replyService;

    @ApiOperation(value = "保存回复信息", notes = "保存回复信息")
    @PostMapping("/save")
    @Log(title = "评论回复管理", businessType = BusinessType.INSERT, message = "保存回复信息")
    public Result saveReply(@RequestBody @Validated ReplySaveDTO replySaveDTO){
        replyService.saveReply(replySaveDTO);
        return Result.ok();
    }


    @ApiOperation(value = "删除回复表信息", notes = "删除回复表信息")
    @DeleteMapping("/delete")
    @Log(title = "回复表", businessType = BusinessType.SELECT, message = "删除回复表信息")
    public Result delete(@RequestBody Long[] ids){
		replyService.removeByIds(Arrays.asList(ids));

        return Result.ok();
    }

}
