package com.qingfeng.electronic.modules.front.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.electronic.modules.front.message.domain.entity.Comment;
import org.springframework.stereotype.Repository;

/**
 * 主评论表
 * 
 * @author 王淮洋
 * @date 2023-05-13 17:50:15
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
	
}
