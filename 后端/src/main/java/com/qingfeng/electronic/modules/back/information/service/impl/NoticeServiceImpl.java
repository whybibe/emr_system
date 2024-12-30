package com.qingfeng.electronic.modules.back.information.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.modules.back.information.dao.NoticeMapper;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospNoticeSaveDTO;
import com.qingfeng.electronic.modules.back.information.domain.dto.HospNoticeUpdateDTO;
import com.qingfeng.electronic.modules.back.information.domain.entity.HospNotice;
import com.qingfeng.electronic.modules.back.information.domain.enums.NoticeTypeEnum;
import com.qingfeng.electronic.modules.back.information.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 医院公告信息表
 *
 * @author 王淮洋
 * @date 2023-04-14 14:47:17
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, HospNotice> implements NoticeService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 查询医院公告信息列表
     *
     * @param page
     * @param limit
     * @param title
     * @param noticeType
     * @return
     */
    @Override
    public Page<HospNotice> findHospNoticeByPage(Long page, Long limit, String title, NoticeTypeEnum noticeType) {
        LambdaQueryWrapper<HospNotice> queryWrapper = new LambdaQueryWrapper<HospNotice>()
                .orderByDesc(BaseEntity::getCreateTime);
        if (StrUtil.isNotBlank(title)) {
            queryWrapper.like(HospNotice::getTitle, "%" + title + "%");
        }
        if (ObjectUtil.isNotNull(noticeType)) {
            queryWrapper.eq(HospNotice::getNoticeType, noticeType);
        }
        return baseMapper.selectPage(new Page<>(page, limit), queryWrapper);
    }

    /**
     * 保存医院公告信息
     *
     * @param hospNoticeSaveDTO
     * @param userId
     * @param userName
     */
    @Override
    public void saveHospNotice(HospNoticeSaveDTO hospNoticeSaveDTO, Long userId, String userName) {
        HospNotice hospNotice = dozerUtils.map2(hospNoticeSaveDTO, HospNotice.class);
        hospNotice.setUserId(userId);
        hospNotice.setAuthor(userName);
        baseMapper.insert(hospNotice);
    }

    /**
     * 修改医院公告信息
     *
     * @param hospNoticeUpdateDTO
     * @param userId
     * @param userName
     */
    @Override
    public void updateHospNoticeById(HospNoticeUpdateDTO hospNoticeUpdateDTO, Long userId, String userName) {
        HospNotice hospNotice = dozerUtils.map2(hospNoticeUpdateDTO, HospNotice.class);
        hospNotice.setUserId(userId)
                .setAuthor(userName);
        baseMapper.updateById(hospNotice);
    }

    /**
     * 查询平台前五条公告信息
     *
     * @return
     */
    @Override
    public List<HospNotice> pinTaiNoticeForFive() {
        Page<HospNotice> noticePage = baseMapper.selectPage(new Page<HospNotice>(0, 5),
                new LambdaQueryWrapper<HospNotice>()
                        .eq(HospNotice::getNoticeType, NoticeTypeEnum.PIN_TAI)
                        .orderByDesc(BaseEntity::getCreateTime)
        );
        return noticePage.getRecords();
    }

    /**
     * 分页查询平台公告信息
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public IPage<HospNotice> pinTaiNoticePage(Long page, Long limit) {
        return baseMapper.selectPage(
                new Page<HospNotice>(page, limit),
                new LambdaQueryWrapper<HospNotice>()
                        .eq(HospNotice::getNoticeType, NoticeTypeEnum.PIN_TAI)
                        .orderByDesc(BaseEntity::getCreateTime)
        );
    }

    /**
     * 修改医院公告的阅读量
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = GeneralAuthException.class)
    public void readingNumById(Long id) {
        // 查询出原来的阅读量，然后进行修改
        HospNotice hospNotice = baseMapper.selectById(id);
        hospNotice.setReadingVolume(hospNotice.getReadingVolume() + 1);
        baseMapper.updateById(hospNotice);
    }

    /**
     * 查询医院前五条停诊公告信息
     * @return
     */
    @Override
    public List<HospNotice> stopNoticeForFive() {
        Page<HospNotice> noticePage = baseMapper.selectPage(new Page<HospNotice>(0, 5),
                new LambdaQueryWrapper<HospNotice>()
                        .eq(HospNotice::getNoticeType, NoticeTypeEnum.STOP)
                        .orderByDesc(BaseEntity::getCreateTime)
        );
        return noticePage.getRecords();
    }

    /**
     * 分页查询医院停诊公告信息
     * @param page
     * @param limit
     * @return
     */
    @Override
    public IPage<HospNotice> stopNoticePage(Long page, Long limit) {
        return baseMapper.selectPage(
                new Page<HospNotice>(page, limit),
                new LambdaQueryWrapper<HospNotice>()
                        .eq(HospNotice::getNoticeType, NoticeTypeEnum.STOP)
                        .orderByDesc(BaseEntity::getCreateTime)
        );
    }
}