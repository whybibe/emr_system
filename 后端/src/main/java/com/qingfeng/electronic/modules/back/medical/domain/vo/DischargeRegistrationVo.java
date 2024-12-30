package com.qingfeng.electronic.modules.back.medical.domain.vo;

import com.qingfeng.electronic.modules.back.medical.domain.ro.DischargeRegistrationRo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 出院登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "出院登记表")
public class DischargeRegistrationVo {

    private static final long serialVersionUID = 1L;

    private Long pageNo;

    private Long pageSize;

    private Long total;

    private List<DischargeRegistrationRo> dischargeRegistrationRoList;
}
