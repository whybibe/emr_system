package com.qingfeng.electronic.modules.back.medical.domain.vo;

import com.qingfeng.electronic.base.entity.BaseEntity;
import com.qingfeng.electronic.modules.back.medical.domain.ro.DeathRegistrationRo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 死亡登记表
 *
 * @author 王淮洋
 * @date 2024-01-09 21:38:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "死亡登记表")
public class DeathRegistrationVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long pageNo;

    private Long pageSize;

    private Long total;

    private List<DeathRegistrationRo> deathRegistrationRoList;
}
