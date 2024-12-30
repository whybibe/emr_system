package com.qingfeng.electronic.modules.back.information.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.modules.back.hosp.dao.HospDepartmentMapper;
import com.qingfeng.electronic.modules.back.hosp.domain.entity.HospDepartment;
import com.qingfeng.electronic.modules.back.information.dao.DoctorInfoMapper;
import com.qingfeng.electronic.modules.back.information.domain.entity.DoctorInfo;
import com.qingfeng.electronic.modules.back.information.domain.ro.DoctorInfoRo;
import com.qingfeng.electronic.modules.back.information.domain.vo.DoctorInfoVo;
import com.qingfeng.electronic.modules.back.information.service.DoctorInfoService;
import com.qingfeng.electronic.modules.back.system.dao.SysRoleMapper;
import com.qingfeng.electronic.modules.back.system.dao.SysUserRoleMapper;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysRole;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUser;
import com.qingfeng.electronic.modules.back.system.domain.entity.SysUserRole;
import com.qingfeng.electronic.modules.back.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/2
 */
@Service
public class DoctorInfoServiceImpl extends ServiceImpl<DoctorInfoMapper, DoctorInfo> implements DoctorInfoService {

    private SysUserService sysUserService;
    private SysUserRoleMapper sysUserRoleMapper;
    private SysRoleMapper sysRoleMapper;
    private HospDepartmentMapper hospDepartmentMapper;

    @Autowired
    public DoctorInfoServiceImpl(SysUserService sysUserService,
                                 SysUserRoleMapper sysUserRoleMapper,
                                 SysRoleMapper sysRoleMapper,
                                 HospDepartmentMapper hospDepartmentMapper) {
        this.sysUserService = sysUserService;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.hospDepartmentMapper = hospDepartmentMapper;
    }

    /**
     * 刷新医师信息
     */
    @Override
    public void refreshDoctorInfo() {
        // 同步添加了用户，但是详细信息还没有同步的
        List<SysRole> sysRoles = sysRoleMapper.selectList(
                new LambdaQueryWrapper<SysRole>()
                        .in(SysRole::getRoleCode, Arrays.asList(
                                "PHYSICIAN", "CASE_MANAGER"
                        ))
        );

        if (CollUtil.isNotEmpty(sysRoles)) {
            // 不为null，查询用户信息，同步医师信息
            sysRoles.forEach(s -> {
                // 根据角色查询用户Id
                List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(
                        new LambdaQueryWrapper<SysUserRole>()
                                .eq(SysUserRole::getRoleId, s.getId())
                );
                if (CollUtil.isNotEmpty(sysUserRoles)) {
                    // 过滤出用户Id
                    List<Long> userIds = sysUserRoles.stream()
                            .map(SysUserRole::getUserId)
                            .collect(Collectors.toList());

                    // 查询已同步的医师信息
                    List<DoctorInfo> doctorInfos = baseMapper.selectList(
                            new LambdaQueryWrapper<DoctorInfo>()
                                    .in(DoctorInfo::getUserId, userIds)
                    );

                    List<Long> ids;
                    if (CollUtil.isNotEmpty(doctorInfos)) {
                        Set<Long> userSet = doctorInfos.stream()
                                .map(DoctorInfo::getUserId)
                                .collect(Collectors.toSet());
                        ids = userIds.stream()
                                .filter(userId -> !userSet.contains(userId))
                                .collect(Collectors.toList());
                    } else {
                        ids = userIds;
                    }

                    if (CollUtil.isNotEmpty(ids)) {
                        // 说明还存在未更新的用户
                        this.saveBatch(
                                ids.stream()
                                        .map(id -> DoctorInfo.builder()
                                                .userId(id)
                                                .build()
                                        )
                                        .collect(Collectors.toList())
                        );
                    }
                }

            });
        }

    }

    /**
     * 分页查询医师信息
     *
     * @param page
     * @param limit
     * @param username
     * @param position
     * @return
     */
    @Override
    public DoctorInfoVo findDoctorInfoPage(Long page,
                                           Long limit,
                                           String username,
                                           String position) {
        IPage<SysUser> pageModel = sysUserService.selectUserPage(
                new Page<SysUser>(page, limit),
                username,
                position
        );

        if (pageModel.getTotal() == 0) {
            return DoctorInfoVo.builder()
                    .total(0L)
                    .pageNo(page)
                    .pageSize(limit)
                    .doctorInfoRos(Collections.emptyList())
                    .build();
        }

        List<SysUser> records = pageModel.getRecords();
        // 根据用户Id查询医师详情
        List<DoctorInfo> doctorInfos = baseMapper.selectList(
                new LambdaQueryWrapper<DoctorInfo>()
                        .in(DoctorInfo::getUserId,
                                records.stream()
                                        .map(SysUser::getId)
                                        .collect(Collectors.toList())
                        )
        );

        Map<Long, DoctorInfo> doctorInfoMap = doctorInfos.stream()
                .collect(Collectors.toMap(
                        DoctorInfo::getUserId,
                        Function.identity()
                ));

        List<List<Long>> ids = doctorInfos.stream()
                .filter(d -> CollUtil.isNotEmpty(d.getDepartmentPatient()))
                .map(d -> d.getDepartmentPatient()
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(id -> id != 0L)
                        .collect(Collectors.toList())
                )
                .collect(Collectors.toList());

        List<HospDepartment> hospDepartmentList = Collections.emptyList();
        if (CollUtil.isNotEmpty(ids)) {
            hospDepartmentList = hospDepartmentMapper.selectList(
                            new LambdaQueryWrapper<HospDepartment>()
                                    .in(BaseEntity::getId, ids.stream()
                                            .flatMap(Collection::stream)
                                            .distinct()
                                            .collect(Collectors.toList())
                                    )
                    );
        }

        List<HospDepartment> finalHospDepartmentList = hospDepartmentList;
        List<DoctorInfoRo> doctorInfoRos = records.stream()
                .map(u -> {
                    DoctorInfo doctorInfo = doctorInfoMap.get(u.getId());
                    DoctorInfoRo doctorInfoRo = DoctorInfoRo.builder()
                            .username(u.getUsername())
                            .name(u.getName())
                            .phone(u.getPhone())
                            .headUrl(u.getHeadUrl())
                            .status(u.getStatus())
                            .build();

                    Optional.ofNullable(doctorInfo)
                            .ifPresent(d -> {
                                doctorInfoRo.setId(d.getId());
                                doctorInfoRo.setPosition(d.getPosition());
                                doctorInfoRo.setIntroduction(d.getIntroduction());
                                doctorInfoRo.setDepartmentPatient(
                                        CollUtil.isEmpty(d.getDepartmentPatient()) ?
                                                "" :
                                                finalHospDepartmentList.stream()
                                                        .filter(h -> d.getDepartmentPatient()
                                                                .stream()
                                                                .flatMap(Collection::stream)
                                                                .filter(id -> id != 0L)
                                                                .collect(Collectors.toSet())
                                                                .contains(h.getId())
                                                        )
                                                        .map(h -> h.getDepName() + "->" +h.getPatientName())
                                                        .collect(Collectors.joining(","))
                                );

                                doctorInfoRo.setWeekday(
                                        CollUtil.isEmpty(d.getWeekday()) ?
                                                "" : d.getWeekday()
                                                .stream()
                                                .collect(Collectors.joining("、"))
                                );
                            });

                    return doctorInfoRo;
                })
                .collect(Collectors.toList());


        return DoctorInfoVo.builder()
                .total(pageModel.getTotal())
                .pageNo(pageModel.getPages())
                .pageSize(pageModel.getSize())
                .doctorInfoRos(doctorInfoRos)
                .build();
    }
}
