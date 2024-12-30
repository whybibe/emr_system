package com.qingfeng.electronic.modules.back.hosp.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString
@HeadStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT)
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
@ApiModel(description = "科室门诊Excel实体类")
public class HospDepartmentExcelVo {

    @ExcelProperty(value = "科室", index = 0)
    @ApiModelProperty(value = "科室的名字 （唯一的）")
    private String depName;

    @ExcelProperty(value = "科室编码", index = 1)
    @ApiModelProperty(value = "科室编码 （唯一的）")
    private String depCode;

    @ExcelProperty(value = "状态", index = 2)
    @ApiModelProperty(value = "状态（启用，不启用）")
    private String status;

    @ExcelProperty(value = "门诊", index = 3)
    @ApiModelProperty(value = "科室下的门诊名")
    private String patientName;

    @ExcelProperty(value = "门诊编号", index = 4)
    @ApiModelProperty(value = "门诊编号")
    private String patientCode;

    @ExcelProperty(value = "门诊介绍", index = 5)
    @ApiModelProperty(value = "门诊介绍")
    private String intro;
}

