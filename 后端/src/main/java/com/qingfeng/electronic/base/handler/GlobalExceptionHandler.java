package com.qingfeng.electronic.base.handler;

import cn.hutool.core.util.ObjectUtil;
import com.qingfeng.electronic.base.util.result.Result;
import com.qingfeng.electronic.base.util.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/4/2
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * JSR303校验出错的异常
     * <p>
     * Map<String, String> errorMap = new HashMap<>(16);
     * bindingResult.getFieldErrors().forEach(fieldError
     * -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
     *
     * @param e  JSR303校验异常信息
     * @return 异常结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型：{}", e.getMessage(), e.getClass());
        // 优化后的代码
        Map<String, String> errorMap = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> ObjectUtil.isNotNull(fieldError.getDefaultMessage())
                                ? fieldError.getDefaultMessage() : ResultCodeEnum.FAIL.getMessage()
                ));

        return Result.fail(errorMap, errorMap.toString());
    }

    @ExceptionHandler(GeneralAuthException.class)
    @ResponseBody
    public Result error(GeneralAuthException e) {
        e.printStackTrace();
        return Result.fail()
                .message(e.getMessage())
                .code(e.getCode());
    }

    /**
     * spring security异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.build(null, ResultCodeEnum.PERMISSION);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail(e.getMessage(), e.getMessage());
    }

}
