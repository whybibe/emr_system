package com.qingfeng.electronic.modules.front.message.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.modules.front.login.domain.entity.UserInfo;
import com.qingfeng.electronic.modules.front.login.service.UserInfoService;
import com.qingfeng.electronic.modules.front.message.dao.CommentMapper;
import com.qingfeng.electronic.modules.front.message.domain.dto.CommentSaveDTO;
import com.qingfeng.electronic.modules.front.message.domain.entity.Comment;
import com.qingfeng.electronic.modules.front.message.domain.entity.Reply;
import com.qingfeng.electronic.modules.front.message.domain.ro.CommentRo;
import com.qingfeng.electronic.modules.front.message.domain.ro.ReplyRo;
import com.qingfeng.electronic.modules.front.message.domain.vo.CommentPageVo;
import com.qingfeng.electronic.modules.front.message.service.CommentService;
import com.qingfeng.electronic.modules.front.message.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 主评论表
 *
 * @author 王淮洋
 * @date 2023-05-13 17:50:15
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存主评论信息
     *
     * @param commentSaveDTO
     */
    @Override
    public void saveComment(CommentSaveDTO commentSaveDTO) {
        baseMapper.insert(dozerUtils.map2(commentSaveDTO, Comment.class));
    }

    /**
     * 主评论分页列表
     *
     * @param page
     * @param limit
     * @param msgId
     * @return
     */
    @Override
    public CommentPageVo listComment(Long page, Long limit, Long msgId) {
        Page<Comment> commentPage = baseMapper.selectPage(
                new Page<Comment>(page, limit),
                new LambdaQueryWrapper<Comment>()
                        .eq(Comment::getMsgId, msgId)
                        .orderByDesc(BaseEntity::getCreateTime)
        );

        if (commentPage.getTotal() == 0) {
            return CommentPageVo.builder()
                    .total(commentPage.getTotal())
                    .pageSize(commentPage.getPages())
                    .limit(commentPage.getSize())
                    .commentRoList(Collections.emptyList())
                    .build();
        }

        List<Comment> commentList = commentPage.getRecords();

        // 查询子评论
        List<Reply> replyList = replyService.list(new LambdaQueryWrapper<Reply>()
                .in(
                        Reply::getCommentId, commentList.stream()
                                .map(Comment::getId)
                                .collect(Collectors.toList())
                )
        );

        Map<Long, List<Reply>> replyMap = replyList.stream()
                .collect(Collectors.groupingBy(
                        Reply::getCommentId
                ));

        // 过滤出用Id
        Set<Long> userIds = commentList.stream()
                .map(Comment::getUserId)
                .collect(Collectors.toSet());
        userIds.addAll(
                replyList.stream()
                        .map(Reply::getUserId)
                        .collect(Collectors.toSet())
        );

        // 查询用户信息
        Map<Long, UserInfo> userInfoMap = userInfoService.list(
                        new LambdaQueryWrapper<UserInfo>()
                                .in(UserInfo::getId, userIds)
                )
                .stream()
                .collect(
                        Collectors.toMap(
                                UserInfo::getId,
                                Function.identity()
                        )
                );

        return CommentPageVo.builder()
                .total(commentPage.getTotal())
                .pageSize(commentPage.getPages())
                .limit(commentPage.getSize())
                .commentRoList(
                        dozerUtils.mapList(commentList, CommentRo.class)
                                .stream()
                                .map(c -> {
                                    UserInfo userInfo = userInfoMap.get(c.getUserId());
                                    c.setHeadImgUrl(userInfo.getHeadImgUrl());
                                    c.setNickName(userInfo.getNickName());
                                    c.setName(userInfo.getName());
                                    c.setAccount(userInfo.getAccount());

                                    // 拼装回复数据
                                    List<Reply> replies = replyMap.get(c.getId());
                                    if (CollUtil.isEmpty(replies)) {
                                        c.setReply(Collections.emptyList());
                                    } else {
                                        // 构造回复数据
                                        c.setReply(
                                                dozerUtils.mapList(replies, ReplyRo.class)
                                                        .stream()
                                                        .map(r -> {
                                                            UserInfo info = userInfoMap.get(r.getUserId());
                                                            r.setHeadImgUrl(info.getHeadImgUrl());
                                                            r.setNickName(info.getNickName());
                                                            r.setName(info.getName());
                                                            r.setAccount(info.getAccount());
                                                            UserInfo toInfo = userInfoMap.get(r.getToId());
                                                            r.setToName(StrUtil.isBlank(toInfo.getNickName()) ?
                                                                    StrUtil.isBlank(toInfo.getName()) ?
                                                                            toInfo.getAccount() : toInfo.getName() : toInfo.getNickName());
                                                            return r;
                                                        })
                                                        .collect(Collectors.toList())
                                        );
                                    }
                                    return c;
                                })
                                .collect(Collectors.toList())
                )
                .build();
    }
}