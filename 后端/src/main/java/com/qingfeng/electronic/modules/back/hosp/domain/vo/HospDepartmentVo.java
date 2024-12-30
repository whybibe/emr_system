package com.qingfeng.electronic.modules.back.hosp.domain.vo;

import com.qingfeng.electronic.modules.back.hosp.domain.ro.HospDepartmentRo;
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
 * 数据库表对应的实体一般放在entity，domain。
 *
 * 如果说数据库实体的结构不能满足我们返回给前端，这个时候一般创建vo实体，vo一般是返回给前端使用的。
 * Ro一般是中间实体，就是介于entity和vo之间，如果vo和entity字段有差异，可以创建一个ro，ro一般用于接收参数或者返回参数，也可以作为中间参数。
 *
 * DTO：一般是接收前端传过的来的参数进行使用的
 *
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "科室门诊信息")
public class HospDepartmentVo {

    @ApiModelProperty(value = "科室的名字 （唯一的）")
    private String depName;

    @ApiModelProperty(value = "科室编码 （唯一的）")
    private String depCode;

    @ApiModelProperty(value = "状态（启用，不启用）")
    private Integer status;

    /**
     * 门诊子集
     */
    List<HospDepartmentRo> hospDepartmentRo;

}

