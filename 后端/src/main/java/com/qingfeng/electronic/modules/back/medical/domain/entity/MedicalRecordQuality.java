package com.qingfeng.electronic.modules.back.medical.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 病案质量管理
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "病案借阅记录表")
@TableName(value = "medical_record_quality", autoResultMap = true)
public class MedicalRecordQuality extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long registrarId;

    @ApiModelProperty(value = "用户名")
    private String registrarName;

    @ApiModelProperty(value = "病案ID")
    private Long admissionRegistrationId;

    @ApiModelProperty(value = "评分")
    private Integer score;

    @ApiModelProperty("评价")
    private String evaluate;
}
