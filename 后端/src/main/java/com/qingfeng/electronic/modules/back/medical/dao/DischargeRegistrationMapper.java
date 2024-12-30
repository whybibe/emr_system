package com.qingfeng.electronic.modules.back.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DischargeRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.ro.DischargeRegistrationRo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 出院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Repository
public interface DischargeRegistrationMapper extends BaseMapper<DischargeRegistration> {

    /**
     * 根据条件查询总记录数
     *
     * @param userName
     * @param department
     * @param patient
     * @param wardNumber
     * @param bedNumber
     * @return
     */
    Long selectDischargeRegistrationCount(@Param("id") Long id,
                                          @Param("userName") String userName,
                                          @Param("department") String department,
                                          @Param("patient") String patient,
                                          @Param("wardNumber") String wardNumber,
                                          @Param("bedNumber") String bedNumber,
                                          @Param("dischargeDate") Date dischargeDate);

    /**
     * 查询列表
     *
     * @param userName
     * @param department
     * @param patient
     * @param wardNumber
     * @param bedNumber
     * @param dischargeDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<DischargeRegistrationRo> selectDischargeRegistrationPage(@Param("id") Long id,
                                                                  @Param("userName") String userName,
                                                                  @Param("department") String department,
                                                                  @Param("patient") String patient,
                                                                  @Param("wardNumber") String wardNumber,
                                                                  @Param("bedNumber") String bedNumber,
                                                                  @Param("dischargeDate") Date dischargeDate,
                                                                  @Param("pageNo") Long pageNo,
                                                                  @Param("pageSize") Long pageSize);
}
