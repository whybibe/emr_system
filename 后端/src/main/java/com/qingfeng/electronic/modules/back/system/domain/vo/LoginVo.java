package com.qingfeng.electronic.modules.back.system.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录对象
 * @author 王淮洋
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginVo {

    /**
     * 手机号
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
