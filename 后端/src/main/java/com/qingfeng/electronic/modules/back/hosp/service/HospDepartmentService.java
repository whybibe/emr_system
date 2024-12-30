package com.qingfeng.electronic.modules.back.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.electronic.modules.back.hosp.domain.dto.HospDepartmentSaveDTO;
import com.qingfeng.electronic.modules.back.hosp.domain.dto.HospDepartmentUpdateDTO;
import com.qingfeng.electronic.modules.back.hosp.domain.dto.HospDepartmentUpdateStatusDTO;
import com.qingfeng.electronic.modules.back.hosp.domain.entity.HospDepartment;
import com.qingfeng.electronic.modules.back.hosp.domain.vo.DepPatientVo;
import com.qingfeng.electronic.modules.back.hosp.domain.vo.HospDepartmentVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/31
 */
public interface HospDepartmentService extends IService<HospDepartment> {


    /**
     * 查询科室门诊信息列表
     *
     * @param depName     科室名称
     * @param patientName 门诊名称
     * @return {@link List}<{@link HospDepartmentVo}>
     */
    List<HospDepartmentVo> hospDepartmentList(String depName, String patientName);

    /**
     * 保存科室门诊信息
     * @param hospDepartmentSaveDTO
     */
    void saveHospDepartment(HospDepartmentSaveDTO hospDepartmentSaveDTO);

    /**
     *  更新科室门诊信息
     * @param hospDepartmentUpdateDTO
     */
    void updateHospDepartment(HospDepartmentUpdateDTO hospDepartmentUpdateDTO);

    /**
     * 修改科室门诊状态
     * @param hospDepartmentUpdateStatusDTO
     */
    void updateStatus(HospDepartmentUpdateStatusDTO hospDepartmentUpdateStatusDTO);

    /**
     * 导出科室门诊信息模板
     * @param response
     */
    void exportTemplate(HttpServletResponse response);

    /**
     * 导出科室门诊信息
     * @param response
     */
    void exportDepartment(HttpServletResponse response);

    /**
     *  导入科室门诊信息
     * @param file
     */
    void importDepartment(MultipartFile file);

    /**
     * 查询科室树
     * @return
     */
    List<DepPatientVo> treeDepPatient();
}
