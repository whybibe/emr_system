package com.qingfeng.electronic.modules.front.message.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.controller.BaseController;
import com.qingfeng.electronic.base.log.annotation.Log;
import com.qingfeng.electronic.base.log.enums.BusinessType;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.modules.front.message.domain.dto.MessageBoardSaveDTO;
import com.qingfeng.electronic.modules.front.message.domain.entity.MessageBoard;
import com.qingfeng.electronic.modules.front.message.domain.entity.UserPraise;
import com.qingfeng.electronic.modules.front.message.domain.vo.MessageBoardPageVo;
import com.qingfeng.electronic.modules.front.message.domain.vo.MessageBoardVo;
import com.qingfeng.electronic.modules.front.message.service.MessageBoardService;
import com.qingfeng.electronic.modules.front.message.service.UserPraiseService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 留言板信息
 *
 * @author 王淮洋
 * @date 2023-05-12 01:34:10
 */
@Api(tags = "留言板信息管理")
@RestController
@RequestMapping("/api/info/message/board")
@CrossOrigin
@Validated
public class MessageBoardApiController extends BaseController {

    @Autowired
    private MessageBoardService messageBoardService;
    @Autowired
    private UserPraiseService userPraiseService;
    @Autowired
    private DozerUtils dozerUtils;

    @ApiOperation(value = "保存留言板信息信息", notes = "保存留言板信息信息")
    @PostMapping("/save")
    @Log(title = "留言板管理", businessType = BusinessType.INSERT, message = "保存留言板信息信息")
    public Result saveMessageBoard(@RequestBody @Validated MessageBoardSaveDTO messageBoardSaveDTO) {
        messageBoardService.save(dozerUtils.map2(messageBoardSaveDTO, MessageBoard.class));
        return Result.ok();
    }

    @ApiOperation(value = "留言板分页列表", notes = "留言板分页列表")
    @GetMapping("/list")
    @Log(title = "留言板管理", businessType = BusinessType.SELECT, message = "留言板分页列表")
    public Result<MessageBoardPageVo> messageBoardList(@ApiParam(name = "page", value = "当前页码", required = true)
                                                       @RequestParam("page") Long page,
                                                       @ApiParam(name = "limit", value = "每页记录数", required = true)
                                                       @RequestParam("limit") Long limit) {
        return Result.ok(
                messageBoardService.pageMessageBoard(
                        new Page<MessageBoard>(page, limit)
                )
        );
    }

    @ApiOperation(value = "留言板信息信息", notes = "留言板信息信息")
    @GetMapping("/info/{id}/{userId}")
    @Log(title = "留言板信息", businessType = BusinessType.SELECT, message = "留言板信息信息")
    public Result<MessageBoardVo> messageBoardInfo(
            @PathVariable("id") Long id,
            @PathVariable("userId") Long userId) {
        // 查看详情需要把用户点赞信息一并查出来
        MessageBoardVo messageBoardVo = messageBoardService.messageBoardInfo(id, userId);
        return Result.ok(messageBoardVo);
    }

    @ApiOperation(value = "删除留言板信息信息", notes = "删除留言板信息信息")
    @DeleteMapping("/delete/{id}")
    @Log(title = "留言板信息", businessType = BusinessType.SELECT, message = "删除留言板信息信息")
    public Result delete(@PathVariable("id") Long id) {
        messageBoardService.removeById(id);
        userPraiseService.remove(
                new LambdaQueryWrapper<UserPraise>()
                        .eq(UserPraise::getInformationId, id)
        );
        // TODO 删除的时候将用户点赞，留言信息一并删除

        return Result.ok();
    }

}
