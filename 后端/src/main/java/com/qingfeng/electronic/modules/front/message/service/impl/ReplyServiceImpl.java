package com.qingfeng.electronic.modules.front.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.modules.front.message.dao.ReplyMapper;
import com.qingfeng.electronic.modules.front.message.domain.dto.ReplySaveDTO;
import com.qingfeng.electronic.modules.front.message.domain.entity.Reply;
import com.qingfeng.electronic.modules.front.message.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 回复表
 *
 * @author 王淮洋
 * @date 2023-05-13 17:50:14
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存回复信息
     * @param replySaveDTO
     */
    @Override
    public void saveReply(ReplySaveDTO replySaveDTO) {
        if (replySaveDTO.getContent().contains("@")) {
            replySaveDTO.setContent(replySaveDTO.getContent().substring(replySaveDTO.getContent().indexOf(" ")+1));
        }
        
        baseMapper.insert(dozerUtils.map2(replySaveDTO, Reply.class));
    }
}