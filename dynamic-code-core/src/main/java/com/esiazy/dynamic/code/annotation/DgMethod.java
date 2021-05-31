package com.esiazy.dynamic.code.annotation;

import java.lang.annotation.*;

/**
 * 动态接口目标方法
 *
 * @author wxf
 * @date 2021/4/26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DgMethod {
    String value() default "";
}
