package com.qingfeng.electronic.modules.front.login.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 王淮洋
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description="登录对象")
public class LoginVo {

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "手机号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String code;

    @ApiModelProperty(value = "IP")
    private String ip;
}
