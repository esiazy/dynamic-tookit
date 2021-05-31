package com.esiazy.dynamic.code.annotation;

import java.lang.annotation.*;

/**
 * 动态接口目标控制器
 *
 * @author wxf
 * @date 2021/4/26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DgController {
    String value();
}
