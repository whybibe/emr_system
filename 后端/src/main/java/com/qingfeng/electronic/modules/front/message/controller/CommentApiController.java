package com.qingfeng.electronic.modules.front.message.controller;

import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.front.message.domain.dto.CommentSaveDTO;
import com.qingfeng.electronic.modules.front.message.domain.entity.Comment;
import com.qingfeng.electronic.modules.front.message.domain.vo.CommentPageVo;
import com.qingfeng.electronic.modules.front.message.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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

/**
 * 主评论表
 *
 * @author 王淮洋
 * @date 2023-05-13 17:50:15
 */
@Api(tags = "主评论管理")
@RestController
@RequestMapping("/api/info/comment")
@CrossOrigin
@Validated
public class CommentApiController extends BaseController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "保存主评论信息", notes = "保存主评论信息")
    @PostMapping("/save")
    @Log(title = "主评论管理", businessType = BusinessType.INSERT, message = "保存主评论信息")
    public Result saveComment(@RequestBody @Validated CommentSaveDTO commentSaveDTO) {
        commentService.saveComment(commentSaveDTO);
        return Result.ok();
    }

    @ApiOperation(value = "主评论分页列表", notes = "主评论分页列表")
    @GetMapping("/list")
    @Log(title = "主评论管理", businessType = BusinessType.SELECT, message = "主评论分页列表")
    public Result<CommentPageVo> listComment(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @RequestParam("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @RequestParam("limit") Long limit,
            @ApiParam(name = "msgId", value = "留言Id", required = true)
            @RequestParam("msgId") Long msgId) {
        CommentPageVo commentPageVo = commentService.listComment(page, limit, msgId);
        return Result.ok(commentPageVo);
    }

    @ApiOperation(value = "主评论表信息", notes = "主评论表信息")
    @GetMapping("/info/{id}")
    @Log(title = "主评论表", businessType = BusinessType.SELECT, message = "主评论表信息")
    public Result info(@PathVariable("id") Long id) {
        Comment comment = commentService.getById(id);

        return Result.ok();
    }

    @ApiOperation(value = "修改主评论表信息", notes = "修改主评论表信息")
    @PutMapping("/update")
    @Log(title = "主评论表", businessType = BusinessType.SELECT, message = "修改主评论表信息")
    public Result update(@RequestBody Comment comment) {
        commentService.updateById(comment);

        return Result.ok();
    }

    @ApiOperation(value = "删除主评论表信息", notes = "删除主评论表信息")
    @DeleteMapping("/delete")
    @Log(title = "主评论表", businessType = BusinessType.SELECT, message = "删除主评论表信息")
    public Result delete(@RequestBody Long[] ids) {
        commentService.removeByIds(Arrays.asList(ids));

        return Result.ok();
    }

}
