package com.qingfeng.electronic.modules.front.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.front.message.domain.dto.CommentSaveDTO;
import com.qingfeng.electronic.modules.front.message.domain.entity.Comment;
import com.qingfeng.electronic.modules.front.message.domain.vo.CommentPageVo;

/**
 * 主评论表
 *
 * @author 王淮洋
 * @date 2023-05-13 17:50:15
 */
public interface CommentService extends IService<Comment> {

    /**
     * 保存主评论信息
     * @param commentSaveDTO
     */
    void saveComment(CommentSaveDTO commentSaveDTO);

    /**
     * 主评论分页列表
     * @param page
     * @param limit
     * @param msgId
     * @return
     */
    CommentPageVo listComment(Long page, Long limit, Long msgId);
}

