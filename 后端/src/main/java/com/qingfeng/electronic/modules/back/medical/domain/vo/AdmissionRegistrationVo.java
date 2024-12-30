package com.qingfeng.electronic.modules.back.medical.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.AdmissionRegistrationDetails;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DeathRegistration;
import com.qingfeng.electronic.modules.back.medical.domain.entity.DischargeRegistration;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@ApiModel(description = "入院登记表")
@TableName(value = "admission_registration", autoResultMap = true)
public class AdmissionRegistrationVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private AdmissionRegistration admissionRegistration;

    private List<AdmissionRegistrationDetails> admissionRegistrationDetailsList;

    private DischargeRegistration dischargeRegistration;

    private DeathRegistration deathRegistration;
}
