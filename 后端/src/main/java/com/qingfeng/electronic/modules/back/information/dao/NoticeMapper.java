package com.qingfeng.electronic.modules.back.information.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.electronic.modules.back.information.domain.entity.HospNotice;
import org.springframework.stereotype.Repository;

/**
 * 医院公告信息表
 * 
 * @author 王淮洋
 * @date 2023-04-14 14:47:17
 */
@Repository
public interface NoticeMapper extends BaseMapper<HospNotice> {
	
}
