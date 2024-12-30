package com.qingfeng.electronic.modules.back.medical.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 病案借阅记录表
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "病案借阅记录表")
@TableName(value = "medical_record_borrowing", autoResultMap = true)
public class MedicalRecordBorrowing extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "病案ID")
    private Long admissionRegistrationId;

    @ApiModelProperty(value = "病案类型")
    private String medicalType;

    @ApiModelProperty(value = "借阅到期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime borrowingDuration;

    @ApiModelProperty(value = "病案借阅备注")
    private String borrowingDurationNote;

    @ApiModelProperty(value = "是否归还 否/是")
    private String isReturn;
}
