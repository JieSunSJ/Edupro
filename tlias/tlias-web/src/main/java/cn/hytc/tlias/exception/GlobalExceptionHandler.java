package cn.hytc.tlias.exception;

import cn.hytc.tlias.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingParam(MissingServletRequestParameterException e) {
        log.warn("缺少必填参数: {}", e.getParameterName());
        return Result.error("缺少必填参数: " + e.getParameterName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String paramName = e.getName();
        Object value = e.getValue();
        String targetType = e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "未知";
        log.warn("参数类型转换失败: {} = {} 无法转为 {} 类型", paramName, value, targetType);

        if (value == null || "".equals(value)) {
            return Result.error("参数 '" + paramName + "' 为空字符串，无法转为 " + targetType + "（加 required = false 可解决）");
        }
        return Result.error("参数 '" + paramName + "' 的值 '" + value + "' 格式错误，需要 " + targetType + " 类型");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.error("服务器内部错误: " + e.getMessage());
    }
}