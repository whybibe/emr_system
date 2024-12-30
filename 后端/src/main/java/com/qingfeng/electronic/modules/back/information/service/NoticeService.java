package com.qingfeng.electronic.modules.back.information.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospNoticeSaveDTO;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospNoticeUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.HospNotice;
import com.qingfeng.electronic.modules.back.information.domain.enums.NoticeTypeEnum;

import java.util.List;

/**
 * 医院公告信息表
 *
 * @author 王淮洋
 * @date 2023-04-14 14:47:17
 */
public interface NoticeService extends IService<HospNotice> {

    /**
     * 查询医院公告信息列表
     * @param page
     * @param limit
     * @param title
     * @param noticeType
     * @return
     */
    Page<HospNotice> findHospNoticeByPage(Long page, Long limit, String title, NoticeTypeEnum noticeType);

    /**
     * 保存医院公告信息
     * @param hospNoticeSaveDTO
     * @param userId
     * @param userName
     */
    void saveHospNotice(HospNoticeSaveDTO hospNoticeSaveDTO, Long userId, String userName);

    /**
     * 修改医院公告信息
     * @param hospNoticeUpdateDTO
     * @param userId
     * @param userName
     */
    void updateHospNoticeById(HospNoticeUpdateDTO hospNoticeUpdateDTO, Long userId, String userName);

    /**
     * 查询平台前五条公告信息
     * @return
     */
    List<HospNotice> pinTaiNoticeForFive();

    /**
     * 分页查询平台公告信息
     * @param page
     * @param limit
     * @return
     */
    IPage<HospNotice> pinTaiNoticePage(Long page, Long limit);

    /**
     * 修改医院公告的阅读量
     * @param id
     */
    void readingNumById(Long id);

    /**
     * 查询医院前五条停诊公告信息
     * @return
     */
    List<HospNotice> stopNoticeForFive();

    /**
     * 分页查询医院停诊公告信息
     * @param page
     * @param limit
     * @return
     */
    IPage<HospNotice> stopNoticePage(Long page, Long limit);
}

