package cn.doublehh.advice;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ming 定义全局异常处理
 * @RestControllerAdvice 是@Controlleradvice 与@ResponseBody 的组合注解
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 全局异常捕捉处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public R errorHandler(Exception e) {
        log.error(e.getMessage(), e);
        return R.failed(e.getMessage());
    }

}
