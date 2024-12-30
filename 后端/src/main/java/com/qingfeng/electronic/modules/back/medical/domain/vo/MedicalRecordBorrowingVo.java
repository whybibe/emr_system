package com.qingfeng.electronic.modules.back.medical.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "病案借阅记录表")
@HeadStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.LEFT)
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class MedicalRecordBorrowingVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @ExcelProperty(value = "用户ID", index = 0)
    private Long userId;

    @ApiModelProperty(value = "用户名")
    @ExcelProperty(value = "用户名", index = 1)
    private String userName;

    @ApiModelProperty(value = "病案ID")
    @ExcelProperty(value = "病案ID", index = 2)
    private Long admissionRegistrationId;

    @ApiModelProperty(value = "病案类型")
    @ExcelProperty(value = "病案类型", index = 3)
    private String medicalType;

    @ApiModelProperty(value = "借阅到期时间")
    @ExcelProperty(value = "借阅到期时间", index = 4)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime borrowingDuration;

    @ApiModelProperty(value = "病案借阅备注")
    @ExcelProperty(value = "病案借阅备注", index = 5)
    private String borrowingDurationNote;

    @ApiModelProperty(value = "是否归还 否/是")
    @ExcelProperty(value = "是否归还", index = 6)
    private String isReturn;
}
