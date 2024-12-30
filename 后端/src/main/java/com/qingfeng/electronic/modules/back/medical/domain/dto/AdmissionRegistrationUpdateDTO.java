package com.qingfeng.electronic.modules.back.medical.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.electronic.base.converter.MyFieldTypeHandler;
import com.qingfeng.electronic.modules.back.medical.domain.ro.AllergyHistoryRo;
import com.qingfeng.electronic.modules.back.medical.domain.ro.EmergencyContactGroupRo;
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
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 入院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "入院登记修改实体")
public class AdmissionRegistrationUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键Id")
    @NotNull(message = "主键Id不能为空")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "曾用名")
    private String formerName;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDay;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "体重")
    private Double weight;

    @ApiModelProperty(value = "身高")
    private Double height;

    @ApiModelProperty(value = "婚姻状况")
    private String maritalStatus;

    @ApiModelProperty(value = "民族")
    private String ethnicity;

    @ApiModelProperty(value = "证件类型")
    private String identificationType;

    @ApiModelProperty(value = "证件号")
    private String identificationNumber;

    @ApiModelProperty(value = "出生地")
    private String placeBirth;

    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "国籍")
    private String nationality;

    @ApiModelProperty(value = "文化程度")
    private String educationLevel;

    @ApiModelProperty(value = "联系电话")
    private String contactNumber;

    @ApiModelProperty(value = "Email")
    private String email;

    @ApiModelProperty(value = "宗教信仰")
    private String religiousBelief;

    @ApiModelProperty(value = "医保号")
    private String insuranceNumber;

    @ApiModelProperty(value = "医保类型")
    private String insuranceType;

    @ApiModelProperty(value = "医疗证号")
    private String medicalCertificateNumber;

    @ApiModelProperty(value = "血型")
    private String bloodType;

    @ApiModelProperty(value = "RH型")
    private String rhType;

    @ApiModelProperty(value = "科室门诊主键Id")
    private Long depId;

    @ApiModelProperty(value = "科室")
    private String department;

    @ApiModelProperty(value = "门诊")
    private String patient;

    @ApiModelProperty(value = "医师Id")
    private Long doctorId;

    @ApiModelProperty(value = "医师名称")
    private String doctorName;

    @ApiModelProperty(value = "医师职位")
    private String doctorPosition;

    @ApiModelProperty(value = "病房号")
    private String wardNumber;

    @ApiModelProperty(value = "床号")
    private String bedNumber;

    @ApiModelProperty(value = "入院登记日期")
    private Date registrationDate;

    @ApiModelProperty(value = "登记人")
    private String registrar;

    @ApiModelProperty(value = "登记人职位")
    private String registrarPosition;

    @ApiModelProperty(value = "职业")
    private String occupation;

    @ApiModelProperty(value = "是否本市")
    private String isLocal;

    @ApiModelProperty(value = "户口地址")
    private String registeredAddress;

    @ApiModelProperty(value = "户口邮编")
    private String registeredPostcode;

    @ApiModelProperty(value = "家庭地址")
    private String homeAddress;

    @ApiModelProperty(value = "家庭邮编")
    private String homePostcode;

    @ApiModelProperty(value = "工作单位")
    private String employer;

    @ApiModelProperty(value = "工作地址")
    private String workAddress;

    @ApiModelProperty(value = "单位电话")
    private String workPhone;

    @ApiModelProperty(value = "紧急联系人组")
    @TableField(typeHandler = MyFieldTypeHandler.class)
    private List<EmergencyContactGroupRo> emergencyContactGroup;

    @ApiModelProperty(value = "过敏史")
    @TableField(typeHandler = MyFieldTypeHandler.class)
    private List<AllergyHistoryRo> allergyHistory;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
