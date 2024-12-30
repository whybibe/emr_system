package com.qingfeng.electronic.modules.front.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.front.message.domain.dto.ReplySaveDTO;
import com.qingfeng.electronic.modules.front.message.domain.entity.Reply;

/**
 * 回复表
 *
 * @author 王淮洋
 * @date 2023-05-13 17:50:14
 */
public interface ReplyService extends IService<Reply> {

    /**
     * 保存回复信息
     * @param replySaveDTO
     */
    void saveReply(ReplySaveDTO replySaveDTO);
}

