package com.qingfeng.electronic.modules.back.information.domain.vo;

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
 * @author 王淮洋
 * @version 1.0.0
 * @date 2023/12/25
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
@ApiModel(description = "数据字典Excel实体类")
public class DictExcelVo {

    @ApiModelProperty(value = "主键Id")
    @ExcelProperty(value = "Id", index = 0)
    private Long id;

    @ApiModelProperty(value = "父级Id")
    @ExcelProperty(value = "父级Id", index = 1)
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称", index = 2)
    private String dictName;

    @ApiModelProperty(value = "值")
    @ExcelProperty(value = "值", index = 3)
    private String dictValue;

    @ApiModelProperty(value = "编码")
    @ExcelProperty(value = "编码", index = 4)
    private String dictCode;
}
