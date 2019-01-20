package cn.doublehh.common.annotation;

import java.lang.annotation.*;

/**
 * @author 胡昊
 * Description:权限注解
 * Date: 2019/1/11
 * Time: 20:58
 * Create: DoubleH
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedPermission {

    String value() default "";
    String[] roleIds();
}
