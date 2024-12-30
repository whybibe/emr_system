package com.qingfeng.electronic.modules.back.medical.domain.dto;

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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
@ApiModel(description = "入院登记保存实体")
public class AdmissionRegistrationSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String userName;

    @ApiModelProperty(value = "曾用名")
    private String formerName;

    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不能为空")
    private String gender;

    @ApiModelProperty(value = "出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "出生日期不能为空")
    private Date birthDay;

    @ApiModelProperty(value = "年龄")
    @NotNull(message = "年龄不能为空")
    private Integer age;

    @ApiModelProperty(value = "体重")
    @NotNull(message = "体重不能为空")
    private Double weight;

    @ApiModelProperty(value = "身高")
    @NotNull(message = "身高不能为空")
    private Double height;

    @ApiModelProperty(value = "婚姻状况")
    @NotBlank(message = "婚姻状况不能为空")
    private String maritalStatus;

    @ApiModelProperty(value = "民族")
    @NotBlank(message = "民族不能为空")
    private String ethnicity;

    @ApiModelProperty(value = "证件类型")
    @NotBlank(message = "证件类型不能为空")
    private String identificationType;

    @ApiModelProperty(value = "证件号")
    @NotBlank(message = "证件号不能为空")
    private String identificationNumber;

    @ApiModelProperty(value = "出生地")
    @NotBlank(message = "出生地不能为空")
    private String placeBirth;

    @ApiModelProperty(value = "籍贯")
    @NotBlank(message = "籍贯不能为空")
    private String nativePlace;

    @ApiModelProperty(value = "国籍")
    @NotBlank(message = "国籍不能为空")
    private String nationality;

    @ApiModelProperty(value = "文化程度")
    private String educationLevel;

    @ApiModelProperty(value = "联系电话")
    @NotBlank(message = "联系电话不能为空")
    private String contactNumber;

    @ApiModelProperty(value = "Email")
    private String email;

    @ApiModelProperty(value = "宗教信仰")
    private String religiousBelief;

    @ApiModelProperty(value = "医保号")
    @NotBlank(message = "医保号不能为空")
    private String insuranceNumber;

    @ApiModelProperty(value = "医保类型")
    @NotBlank(message = "医保不能为空")
    private String insuranceType;

    @ApiModelProperty(value = "医疗证号")
    @NotBlank(message = "医疗证号不能为空")
    private String medicalCertificateNumber;

    @ApiModelProperty(value = "血型")
    private String bloodType;

    @ApiModelProperty(value = "RH型")
    private String rhType;

    @ApiModelProperty(value = "科室门诊主键Id")
    private Long depId;

    @ApiModelProperty(value = "科室")
    @NotBlank(message = "科室不能为空")
    private String department;

    @ApiModelProperty(value = "门诊")
    @NotBlank(message = "门诊不能为空")
    private String patient;

    @ApiModelProperty(value = "医师Id")
    @NotNull(message = "医师不能为空")
    private Long doctorId;

    @ApiModelProperty(value = "医师名称")
    private String doctorName;

    @ApiModelProperty(value = "医师职位")
    @NotBlank(message = "医师职位不能为空")
    private String doctorPosition;

    @ApiModelProperty(value = "病房号")
    @NotBlank(message = "病房号不能为空")
    private String wardNumber;

    @ApiModelProperty(value = "床号")
    @NotBlank(message = "床号不能为空")
    private String bedNumber;

    @ApiModelProperty(value = "入院登记日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "住院登记日期不能为空")
    private Date registrationDate;

    @ApiModelProperty(value = "登记人")
    @NotBlank(message = "登记人不能为空")
    private String registrar;

    @ApiModelProperty(value = "登记人职位")
    @NotBlank(message = "登记人职位不能为空")
    private String registrarPosition;

    @ApiModelProperty(value = "职业")
    private String occupation;

    @ApiModelProperty(value = "是否本市")
    @NotBlank(message = "是否本市不能为空")
    private String isLocal;

    @ApiModelProperty(value = "户口地址")
    @NotBlank(message = "糊口地址不能为空")
    private String registeredAddress;

    @ApiModelProperty(value = "户口邮编")
    @NotBlank(message = "糊口邮编不能为空")
    private String registeredPostcode;

    @ApiModelProperty(value = "家庭地址")
    @NotBlank(message = "家庭地址不能为空")
    private String homeAddress;

    @ApiModelProperty(value = "家庭邮编")
    @NotBlank(message = "家庭邮编不能为空")
    private String homePostcode;

    @ApiModelProperty(value = "工作单位")
    @NotBlank(message = "工作单位不能为空")
    private String employer;

    @ApiModelProperty(value = "工作地址")
    @NotBlank(message = "工作地址不能为空")
    private String workAddress;

    @ApiModelProperty(value = "单位电话")
    @NotBlank(message = "单位电话不能为空")
    private String workPhone;

    @ApiModelProperty(value = "紧急联系人组")
    @TableField(typeHandler = MyFieldTypeHandler.class)
    @Valid
    private List<EmergencyContactGroupRo> emergencyContactGroup;

    @ApiModelProperty(value = "过敏史")
    @TableField(typeHandler = MyFieldTypeHandler.class)
    private List<AllergyHistoryRo> allergyHistory;
}
