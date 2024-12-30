package com.qingfeng.electronic.modules.back.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DeathRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.ro.DeathRegistrationRo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 死亡登记表
 * 
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Repository
public interface DeathRegistrationMapper extends BaseMapper<DeathRegistration> {

    /**
     * 根据条件查询死亡登记表数量
     * @param userName
     * @param department
     * @param patient
     * @param wardNumber
     * @param bedNumber
     * @param deathDate
     * @return
     */
    Long selectDeathRegistrationCount(@Param("userName") String userName,
                                      @Param("department") String department,
                                      @Param("patient") String patient,
                                      @Param("wardNumber") String wardNumber,
                                      @Param("bedNumber") String bedNumber,
                                      @Param("deathDate") Date deathDate,
                                      @Param("id") Long id);

    /**
     *  根据条件查询死亡登记表列表
     * @param userName
     * @param department
     * @param patient
     * @param wardNumber
     * @param bedNumber
     * @param deathDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<DeathRegistrationRo> selectDeathRegistrationPage(@Param("userName") String userName,
                                                          @Param("department") String department,
                                                          @Param("patient") String patient,
                                                          @Param("wardNumber") String wardNumber,
                                                          @Param("bedNumber") String bedNumber,
                                                          @Param("deathDate") Date deathDate,
                                                          @Param("id") Long id,
                                                          @Param("pageNo") Long pageNo,
                                                          @Param("pageSize") Long pageSize);
}
