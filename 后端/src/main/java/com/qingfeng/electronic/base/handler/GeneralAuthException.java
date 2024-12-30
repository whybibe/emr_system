package com.qingfeng.electronic.base.handler;

import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/4/2
 */
@Data
@NoArgsConstructor
public class GeneralAuthException extends RuntimeException{

    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param code
     * @param message
     */
    public GeneralAuthException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public GeneralAuthException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GeneralAuthException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
