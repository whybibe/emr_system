package com.qingfeng.electronic.modules.back.information.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.converter.MyFieldTypeHandler;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.modules.back.information.domain.enums.DoctorPositionEnum;
import com.qingfeng.electronic.base.converter.MyListLongFieldTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/1/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "医生详细信息实体")
@TableName(value = "doctor_info", autoResultMap = true)
public class DoctorInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "医师职称")
    private DoctorPositionEnum position;

    @ApiModelProperty(value = "所属科室门诊信息")
    @TableField(typeHandler = MyListLongFieldTypeHandler.class)
    private List<List<Long>> departmentPatient;

    @ApiModelProperty(value = "上班时间")
    @TableField(typeHandler = MyFieldTypeHandler.class)
    private List<String> weekday;

    @ApiModelProperty(value = "医师介绍")
    private String introduction;
}
