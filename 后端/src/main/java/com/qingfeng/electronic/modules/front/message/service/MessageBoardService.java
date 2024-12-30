package com.qingfeng.electronic.modules.front.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.front.message.domain.entity.MessageBoard;
import com.qingfeng.electronic.modules.front.message.domain.vo.MessageBoardPageVo;
import com.qingfeng.electronic.modules.front.message.domain.vo.MessageBoardVo;

/**
 * 留言板信息
 *
 * @author 王淮洋
 * @date 2023-05-12 01:34:10
 */
public interface MessageBoardService extends IService<MessageBoard> {

    /**
     * 分页查询留言板信息
     * @param messageBoardPage
     * @return
     */
    MessageBoardPageVo pageMessageBoard(Page<MessageBoard> messageBoardPage);

    /**
     * 查询留言详情信息
     * @param id
     * @param userId
     * @return
     */
    MessageBoardVo messageBoardInfo(Long id, Long userId);
}

