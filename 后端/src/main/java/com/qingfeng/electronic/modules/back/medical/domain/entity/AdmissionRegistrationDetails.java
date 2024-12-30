package com.qingfeng.electronic.modules.back.medical.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.electronic.base.converter.MyFieldTypeHandler;
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

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author 王淮洋
 * @version 1.0.0
 * @date 2024/3/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "入院会诊详情")
@TableName(value = "admission_registration_details", autoResultMap = true)
public class AdmissionRegistrationDetails extends BaseEntity {

    @ApiModelProperty(value = "入院登记表Id")
    @NotNull(message = "入院登记表Id不能为空")
    private Long admissionRegistrationId;

    @ApiModelProperty(value = "会诊医生Id")
    private Long userId;

    @ApiModelProperty(value = "会诊医生名称")
    private String userName;

    @ApiModelProperty(value = "疾病名称")
    @TableField(typeHandler = MyFieldTypeHandler.class)
    private List<String> diseaseName;

    @ApiModelProperty(value = "会诊时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date contentTime;

    @ApiModelProperty(value = "会诊内容")
    private String content;

    @ApiModelProperty(value = "会诊类型")
    private String admissionType;

    @ApiModelProperty(value = "手术名称")
    private String operationName;

    @ApiModelProperty(value = "手术室编号")
    private String operationRoomNumber;

    @ApiModelProperty(value = "手术医生")
    private String operationDoctor;
}
