package com.qingfeng.electronic.modules.front.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.modules.front.login.domain.entity.UserInfo;
import com.qingfeng.electronic.modules.front.login.service.UserInfoService;
import com.qingfeng.electronic.modules.front.message.dao.MessageBoardMapper;
import com.qingfeng.electronic.modules.front.message.domain.entity.MessageBoard;
import com.qingfeng.electronic.modules.front.message.domain.entity.UserPraise;
import com.qingfeng.electronic.modules.front.message.domain.ro.MessageBoardRo;
import com.qingfeng.electronic.modules.front.message.domain.vo.MessageBoardPageVo;
import com.qingfeng.electronic.modules.front.message.domain.vo.MessageBoardVo;
import com.qingfeng.electronic.modules.front.message.service.MessageBoardService;
import com.qingfeng.electronic.modules.front.message.service.UserPraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 留言板信息
 *
 * @author 王淮洋
 * @date 2023-05-12 01:34:10
 */
@Service
public class MessageBoardServiceImpl extends ServiceImpl<MessageBoardMapper, MessageBoard> implements MessageBoardService {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserPraiseService userPraiseService;
    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 分页查询留言板信息
     *
     * @param messageBoardPage
     * @return
     */
    @Override
    public MessageBoardPageVo pageMessageBoard(Page<MessageBoard> messageBoardPage) {
        Page<MessageBoard> messageBoards = baseMapper.selectPage(messageBoardPage,
                new LambdaQueryWrapper<MessageBoard>()
                        .orderByDesc(BaseEntity::getCreateTime));

        if (messageBoards.getTotal() == 0) {
            return MessageBoardPageVo.builder()
                    .total(messageBoards.getTotal())
                    .pageSize(messageBoards.getPages())
                    .limit(messageBoards.getSize())
                    .messageBoardRoList(Collections.emptyList())
                    .build();
        }

        // 拿到分页的留言信息
        List<MessageBoard> messageBoardList = messageBoards.getRecords();

        // 过滤出用户Id
        List<Long> userIds = messageBoardList.stream()
                .map(MessageBoard::getUserId)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, UserInfo> userInfoMap = userInfoService.list(
                        new LambdaQueryWrapper<UserInfo>()
                                .in(UserInfo::getId, userIds)
                )
                .stream()
                .collect(Collectors.toMap(
                                UserInfo::getId,
                                Function.identity()
                        )
                );

        // 组装数据
        return MessageBoardPageVo.builder()
                .total(messageBoards.getTotal())
                .pageSize(messageBoards.getPages())
                .limit(messageBoards.getSize())
                .messageBoardRoList(
                        messageBoardList.stream()
                                .map(m -> MessageBoardRo.builder()
                                        .id(m.getId())
                                        .userId(m.getUserId())
                                        .messageContent(m.getMessageContent())
                                        .likes(m.getLikes())
                                        .poorPraise(m.getPoorPraise())
                                        .createTime(m.getCreateTime())
                                        .updateTime(m.getUpdateTime())
                                        .headImgUrl(userInfoMap.get(m.getUserId()).getHeadImgUrl())
                                        .nickName(userInfoMap.get(m.getUserId()).getNickName())
                                        .account(userInfoMap.get(m.getUserId()).getAccount())
                                        .name(userInfoMap.get(m.getUserId()).getName())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }

    /**
     * 查询留言详情信息
     *
     * @param id
     * @param userId
     * @return
     */
    @Override
    public MessageBoardVo messageBoardInfo(Long id, Long userId) {
        // 根据id查询留言信息
        MessageBoard messageBoard = baseMapper.selectById(id);
        // 根据用户Id查询用户点赞信息
        UserPraise userPraise = userPraiseService.getOne(
                new LambdaQueryWrapper<UserPraise>()
                        .eq(UserPraise::getInformationId, id)
                        .eq(UserPraise::getUserId, userId)
        );

        UserInfo userInfo = userInfoService.getById(messageBoard.getUserId());
        MessageBoardVo messageBoardVo = dozerUtils.map2(messageBoard, MessageBoardVo.class);
        messageBoardVo.setUserPraise(userPraise);
        messageBoardVo.setHeadImgUrl(userInfo.getHeadImgUrl());
        messageBoardVo.setNickName(userInfo.getNickName());
        messageBoardVo.setName(userInfo.getName());
        messageBoardVo.setAccount(userInfo.getAccount());
        return messageBoardVo;
    }
}