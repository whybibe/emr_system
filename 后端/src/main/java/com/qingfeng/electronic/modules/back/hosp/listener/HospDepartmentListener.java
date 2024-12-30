package com.qingfeng.electronic.modules.back.hosp.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingfeng.electronic.modules.back.hosp.domain.entity.HospDepartment;
import com.qingfeng.electronic.modules.back.hosp.domain.vo.HospDepartmentExcelVo;
import com.qingfeng.electronic.modules.back.hosp.service.HospDepartmentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/1
 */
public class HospDepartmentListener extends AnalysisEventListener<HospDepartmentExcelVo> {

    private HospDepartmentService hospDepartmentService;

    public HospDepartmentListener(HospDepartmentService hospDepartmentService) {
        this.hospDepartmentService = hospDepartmentService;
    }


    /**
     * 每隔500条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存数据，新增或者修改
     */
    private List<HospDepartment> cachedAddList = new ArrayList<>(BATCH_COUNT);
    private List<HospDepartment> cachedUpdateList = new ArrayList<>(BATCH_COUNT);


    @Override
    public void invoke(HospDepartmentExcelVo hospDepartmentExcelVo, AnalysisContext analysisContext) {
        // 科室名和门诊名不能重复
        HospDepartment hospDepartment = hospDepartmentService.getOne(
                new LambdaQueryWrapper<HospDepartment>()
                        .eq(HospDepartment::getDepName, hospDepartmentExcelVo.getDepName())
                        .eq(HospDepartment::getPatientName, hospDepartmentExcelVo.getPatientName())
        );

        if (ObjectUtil.isEmpty(hospDepartment)) {
            // 不存在， 就可以直接进行存储
            cachedAddList.add(
                    HospDepartment.builder()
                            .depCode(hospDepartmentExcelVo.getDepCode())
                            .depName(hospDepartmentExcelVo.getDepName())
                            .patientName(hospDepartmentExcelVo.getPatientName())
                            .patientCode(hospDepartmentExcelVo.getPatientCode())
                            .intro(hospDepartmentExcelVo.getIntro())
                            .status(hospDepartmentExcelVo.getStatus().equals("启用") ? 1 : 0)
                            .build()
            );
        } else {
            // 已经存在。则更新
            hospDepartment.setStatus(hospDepartmentExcelVo.getStatus().equals("启用") ? 1 : 0)
                    .setDepName(hospDepartmentExcelVo.getDepName())
                    .setDepCode(hospDepartmentExcelVo.getDepCode())
                    .setPatientName(hospDepartmentExcelVo.getPatientName())
                    .setPatientCode(hospDepartment.getPatientCode())
                    .setIntro(hospDepartmentExcelVo.getIntro());
            cachedUpdateList.add(hospDepartment);
        }

        // 执行
        if (cachedAddList.size() > BATCH_COUNT) {
            hospDepartmentService.saveBatch(cachedAddList);
            cachedAddList.clear();
        }
        if (cachedUpdateList.size() > BATCH_COUNT) {
            hospDepartmentService.updateBatchById(cachedUpdateList);
            cachedUpdateList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 执行
        if (CollUtil.isNotEmpty(cachedAddList)) {
            hospDepartmentService.saveBatch(cachedAddList);
        }
        if (CollUtil.isNotEmpty(cachedUpdateList)) {
            hospDepartmentService.updateBatchById(cachedUpdateList);
        }
    }
}
