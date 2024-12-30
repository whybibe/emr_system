package com.qingfeng.electronic.modules.back.information.domain.dto;

import com.qingfeng.electronic.modules.back.information.domain.enums.DoctorPositionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/7
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInfoUpdateDTO {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空")
    private Long id;

    /**
     * 用户Id
     */
    @NotNull
    private Long userId;

    /**
     * 医师职称
     */
    @NotNull(message = "医师职称不能为空")
    private DoctorPositionEnum position;

    /**
     * 科室门诊信息
     */
    @NotNull(message = "科室不能为空")
    private List<List<Long>> departmentPatient;

    @ApiModelProperty(value = "上班时间")
    @NotNull(message = "上班时间不能为空")
    private List<String> weekday;

    @NotBlank(message = "主治简介不能为空")
    private String introduction;
}
