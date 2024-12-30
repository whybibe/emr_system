package com.qingfeng.electronic.modules.front.message.domain.dto;

import com.qingfeng.electronic.modules.front.message.domain.enums.IsSeeEnum;
import com.qingfeng.electronic.modules.front.message.domain.enums.UserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 消息通知表
 *
 * @author 王淮洋
 * @date 2023-05-03 22:53:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "消息通知保存实体")
public class NewsNotifySaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户Id")
    @NotNull(message = "用户Id不能为空")
    private Long userId;

    @ApiModelProperty(value = "消息标题")
    @NotBlank(message = "消息标题不能为空")
    private String newsTitle;

    @ApiModelProperty(value = "用户类型")
    @NotNull(message = "用户类型不能为空")
    private UserTypeEnum userType;

    @ApiModelProperty(value = "消息内容  如：XXX同学，你的某某活动即将截止，请及时评价加分，避免活动失效！！！")
    @NotBlank(message = "消息内容不能为空")
    private String newsContent;

    @ApiModelProperty(value = "是否已查看 （未查看   已查看）  查看了就不再做显示（枚举处理）")
    @NotNull(message = "是否已查看不能为空")
    private IsSeeEnum isSee;

}
