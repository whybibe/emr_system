package com.qingfeng.electronic.modules.back.hosp.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.electronic.base.handler.GeneralAuthException;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import com.qingfeng.electronic.modules.back.hosp.dao.HospDepartmentMapper;
import com.qingfeng.electronic.modules.back.hosp.domain.dto.HospDepartmentSaveDTO;
import com.qingfeng.electronic.modules.back.hosp.domain.dto.HospDepartmentUpdateDTO;
import com.qingfeng.electronic.modules.back.hosp.domain.dto.HospDepartmentUpdateStatusDTO;
import com.qingfeng.electronic.modules.back.hosp.domain.entity.HospDepartment;
import com.qingfeng.electronic.modules.back.hosp.domain.ro.HospDepartmentRo;
import com.qingfeng.electronic.modules.back.hosp.domain.vo.DepPatientVo;
import com.qingfeng.electronic.modules.back.hosp.domain.vo.HospDepartmentExcelVo;
import com.qingfeng.electronic.modules.back.hosp.domain.vo.HospDepartmentVo;
import com.qingfeng.electronic.modules.back.hosp.listener.HospDepartmentListener;
import com.qingfeng.electronic.modules.back.hosp.service.HospDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/31
 */
@Service
public class HospDepartmentServiceImpl extends ServiceImpl<HospDepartmentMapper, HospDepartment> implements HospDepartmentService {

    private DozerUtils dozerUtils;

    @Autowired
    public HospDepartmentServiceImpl(DozerUtils dozerUtils) {
        this.dozerUtils = dozerUtils;
    }

    /**
     * 查询科室门诊信息
     *
     * @param depName     科室名称
     * @param patientName 门诊名称
     * @return
     */
    @Override
    public List<HospDepartmentVo> hospDepartmentList(String depName, String patientName) {
        List<HospDepartment> hospDepartments = baseMapper.selectList(
                new LambdaQueryWrapper<HospDepartment>()
                        .eq(StrUtil.isNotBlank(depName), HospDepartment::getDepName, depName)
                        .eq(StrUtil.isNotBlank(patientName), HospDepartment::getPatientName, patientName)
        );

        // 如果没有查到数据，直接返回空集合
        if (CollUtil.isEmpty(hospDepartments)) {
            return Collections.emptyList();
        }

        // 封装科室map进行数据封装
        return hospDepartments.stream()
                .collect(Collectors.groupingBy(HospDepartment::getDepName))
                .entrySet()
                .stream()
                .map(entry -> HospDepartmentVo.builder()
                        .depName(entry.getKey())
                        .depCode(entry.getValue().get(0).getDepCode())
                        .status(
                                ((Long) entry.getValue()
                                        .stream()
                                        .filter(h -> h.getStatus() == 0)
                                        .count())
                                        .intValue() == entry.getValue().size() ? 0 : 1
                        )
                        .hospDepartmentRo(
                                entry.getValue()
                                        .stream()
                                        .map(h -> HospDepartmentRo.builder()
                                                .id(h.getId())
                                                .patientName(h.getPatientName())
                                                .patientCode(h.getPatientCode())
                                                .intro(h.getIntro())
                                                .status(h.getStatus())
                                                .build()
                                        )
                                        .collect(Collectors.toList())
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }

    /**
     * 保存科室门诊信息
     *
     * @param hospDepartmentSaveDTO
     */
    @Override
    public void saveHospDepartment(HospDepartmentSaveDTO hospDepartmentSaveDTO) {
        // 校验科室名和门诊名是否唯一
        List<HospDepartment> hospDepartments = baseMapper.selectList(
                new LambdaQueryWrapper<HospDepartment>()
                        .eq(HospDepartment::getDepName, hospDepartmentSaveDTO.getDepName())
                        .eq(HospDepartment::getPatientName, hospDepartmentSaveDTO.getPatientName())
        );

        if (CollUtil.isNotEmpty(hospDepartments)) {
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "科室门诊信息必须唯一存在");
        }

        // 检验科室编码是否唯一
        hospDepartments = baseMapper.selectList(
                new LambdaQueryWrapper<HospDepartment>()
                        .eq(HospDepartment::getDepCode, hospDepartmentSaveDTO.getDepCode())
                        .ne(HospDepartment::getDepName, hospDepartmentSaveDTO.getDepName())
        );

        if (CollUtil.isNotEmpty(hospDepartments)) {
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "科室编码必须唯一存在");
        }

        // 校验门诊编码是否唯一存在
        hospDepartments = baseMapper.selectList(
                new LambdaQueryWrapper<HospDepartment>()
                        .eq(HospDepartment::getPatientCode, hospDepartmentSaveDTO.getPatientCode())
        );

        if (CollUtil.isNotEmpty(hospDepartments)) {
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "门诊编码必须唯一存在");
        }

        // 进行保存
        baseMapper.insert(dozerUtils.map2(hospDepartmentSaveDTO, HospDepartment.class));
    }

    /**
     * 更新科室门诊信息
     *
     * @param hospDepartmentUpdateDTO
     */
    @Override
    public void updateHospDepartment(HospDepartmentUpdateDTO hospDepartmentUpdateDTO) {
        // 判断是修改科室还是修改门诊信息  修改科室——》没有门诊信息
        if (StrUtil.isNotBlank(hospDepartmentUpdateDTO.getOldPatientName())) {
            // 修改门诊信息。检查门诊名是否重复
            if (!hospDepartmentUpdateDTO.getOldPatientName().equals(hospDepartmentUpdateDTO.getPatientName())) {
                List<HospDepartment> hospDepartments = baseMapper.selectList(
                        new LambdaQueryWrapper<HospDepartment>()
                                .eq(HospDepartment::getDepName, hospDepartmentUpdateDTO.getDepName())
                                .eq(HospDepartment::getPatientName, hospDepartmentUpdateDTO.getPatientName())
                );

                if (CollUtil.isNotEmpty(hospDepartments)) {
                    throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "门诊名称必须唯一存在");
                }
            }

            if (!hospDepartmentUpdateDTO.getOldPatientCode().equals(hospDepartmentUpdateDTO.getPatientCode())) {
                List<HospDepartment> hospDepartments = baseMapper.selectList(
                        new LambdaQueryWrapper<HospDepartment>()
                                .eq(HospDepartment::getPatientCode, hospDepartmentUpdateDTO.getPatientCode())
                );
                if (CollUtil.isNotEmpty(hospDepartments)) {
                    throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "门诊编码必须唯一存在");
                }
            }

            this.updateBatchById(
                    baseMapper.selectList(
                                    new LambdaQueryWrapper<HospDepartment>()
                                            .eq(HospDepartment::getDepName, hospDepartmentUpdateDTO.getOldDepName())
                                            .eq(HospDepartment::getPatientCode, hospDepartmentUpdateDTO.getOldPatientCode())
                            )
                            .stream()
                            .peek(h -> h.setPatientName(hospDepartmentUpdateDTO.getPatientName())
                                    .setPatientCode(hospDepartmentUpdateDTO.getPatientCode())
                                    .setIntro(hospDepartmentUpdateDTO.getIntro()))
                            .collect(Collectors.toList())
            );
        } else {
            // 修改科室信息
            if (!hospDepartmentUpdateDTO.getDepName().equals(hospDepartmentUpdateDTO.getOldDepName())) {
                List<HospDepartment> hospDepartments = baseMapper.selectList(
                        new LambdaQueryWrapper<HospDepartment>()
                                .eq(HospDepartment::getDepName, hospDepartmentUpdateDTO.getDepName())
                );

                if (CollUtil.isNotEmpty(hospDepartments)) {
                    throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "科室名称已存在");
                }
            }

            // 校验科室编码
            if (!hospDepartmentUpdateDTO.getDepCode().equals(hospDepartmentUpdateDTO.getOldDepCode())) {
                List<HospDepartment> hospDepartments = baseMapper.selectList(
                        new LambdaQueryWrapper<HospDepartment>()
                                .eq(HospDepartment::getDepCode, hospDepartmentUpdateDTO.getDepCode())
                );

                if (CollUtil.isNotEmpty(hospDepartments)) {
                    throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), "科室编码已存在");
                }
            }

            // 查询出包含所有科室的数据进行更新
            this.updateBatchById(
                    baseMapper.selectList(
                                    new LambdaQueryWrapper<HospDepartment>()
                                            .eq(HospDepartment::getDepName, hospDepartmentUpdateDTO.getOldDepName())
                            )
                            .stream()
                            .map(h -> {
                                h.setDepName(hospDepartmentUpdateDTO.getDepName());
                                h.setDepCode(hospDepartmentUpdateDTO.getDepCode());
                                return h;
                            })
                            .collect(Collectors.toList())
            );
        }
    }

    /**
     * 修改科室门诊状态
     *
     * @param hospDepartmentUpdateStatusDTO
     */
    @Override
    public void updateStatus(HospDepartmentUpdateStatusDTO hospDepartmentUpdateStatusDTO) {
        if (ObjectUtil.isNotNull(hospDepartmentUpdateStatusDTO.getId())) {
            // 修改门诊状态
            baseMapper.updateById(
                    baseMapper.selectById(hospDepartmentUpdateStatusDTO.getId())
                            .setStatus(hospDepartmentUpdateStatusDTO.getStatus())
            );
        } else {
            // 修改科室下的所有门诊信息
            this.updateBatchById(
                    baseMapper.selectList(
                                    new LambdaQueryWrapper<HospDepartment>()
                                            .eq(HospDepartment::getDepName, hospDepartmentUpdateStatusDTO.getDepName())
                            )
                            .stream()
                            .map(h -> h.setStatus(hospDepartmentUpdateStatusDTO.getStatus()))
                            .collect(Collectors.toList())
            );
        }
    }

    /**
     * 导出科室门诊信息模板
     * @param response
     */
    @Override
    public void exportTemplate(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel导没有关系，处理的是文件的命名的乱码
            response.setHeader("Content-disposition",
                    "attachment;filename=" +
                            URLEncoder.encode("科室门诊列表模板", "UTF-8") +
                            ".xlsx");

            // 调用方法实现写操作
            EasyExcelFactory.write(response.getOutputStream(), HospDepartmentExcelVo.class)
                    .sheet("科室门诊列表模板")
                    .doWrite(Collections.emptyList());
        } catch (Exception e) {
            log.error("科室门诊列表模板导出出现未知异常，异常信息如下：", e);
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 导出科室门诊信息
     * @param response
     */
    @Override
    public void exportDepartment(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel导没有关系，处理的是文件的命名的乱码
            response.setHeader("Content-disposition",
                    "attachment;filename=" +
                            URLEncoder.encode("科室门诊列表", "UTF-8") +
                            ".xlsx");

            // 调用方法实现写操作
            EasyExcelFactory.write(response.getOutputStream(), HospDepartmentExcelVo.class)
                    .sheet("科室门诊列表")
                    .doWrite(
                            baseMapper.selectList(null)
                                    .stream()
                                    .map(h -> HospDepartmentExcelVo.builder()
                                            .depName(h.getDepName())
                                            .depCode(h.getDepCode())
                                            .patientName(h.getPatientName())
                                            .patientCode(h.getPatientCode())
                                            .status(h.getStatus() == 1 ? "启用":"禁用")
                                            .intro(h.getIntro())
                                            .build())
                                    .collect(Collectors.toList())
                    );
        } catch (Exception e) {
            log.error("科室门诊列表导出出现未知异常，异常信息如下：", e);
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 导入科室门诊信息
     * @param file
     */
    @Override
    public void importDepartment(MultipartFile file) {
        try {
            EasyExcelFactory.read(file.getInputStream(),
                            HospDepartmentExcelVo.class,
                            new HospDepartmentListener(this)
                    )
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            throw new GeneralAuthException(ResultCodeEnum.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 查询科室门诊树
     * @return
     */
    @Override
    public List<DepPatientVo> treeDepPatient() {
        // 查询出所有的科室门诊信息
        List<HospDepartment> hospDepartmentList = baseMapper.selectList(null);
        // 根据科室进行分组
        Map<String, List<HospDepartment>> hospDepartmentMap = hospDepartmentList.stream()
                .collect(Collectors.groupingBy(HospDepartment::getDepName));

        return hospDepartmentMap.entrySet()
                .stream()
                .map(entry -> DepPatientVo.builder()
                        .label(entry.getKey())
                        .value(0L)
                        .children(
                                entry.getValue()
                                        .stream()
                                        .map(h -> DepPatientVo.builder()
                                                .label(h.getPatientName())
                                                .value(h.getId())
                                                .children(null)
                                                .build()
                                        )
                                        .collect(Collectors.toList())
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }
}
